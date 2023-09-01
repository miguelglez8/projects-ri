import json # Para poder trabajar con objetos JSON
import requests
from elasticsearch import Elasticsearch
from elasticsearch import helpers # importamos los helpers para escanear
from urllib.parse import quote

# Ejercicio 2: Generar un volcado con todos los tweets relativos a un tema especificado (cristiano ronaldo)

def main():
    # Password para el usuario 'lectura' asignada por nosotros
    #
    READONLY_PASSWORD = "fHMyaKB8pmzoQeS=k4J4"

    # Creamos el cliente y lo conectamos a nuestro servidor
    #
    global es

    es = Elasticsearch(
        "https://localhost:9200",
        ca_certs="./http_ca.crt",
        basic_auth=("elastic", READONLY_PASSWORD)
    )

    # sacamos los terminos más significativos de los tuits con métrica mutual_information
    results = es.search(request_timeout=500,
        index="tweets-miguel-2",
        body={
            "size": 0,
            "query":{
                "query_string": {
                    "query":"text:\"cristiano ronaldo\" AND lang:en" # le pasamos el tema y el idioma en inglés
                }
            },
            "aggs": {
                 "Most significant users": {
                     "significant_terms": {
                       "field": "text",
                        "size": 22, # irá variando a medida que vayamos analizando los resultados
                        "gnd": { # en este caso, hemos escogido esta métrica
                        }
                     }
                 }
            }
        }
    )

    # sacamos los terminos más significativos de los tuits con métrica gnd
    results_2 = es.search(request_timeout=500,
        index="tweets-miguel-2",
        body={
            "size": 0,
            "query":{
                "query_string": {
                    "query":"text:\"cristiano ronaldo\" AND lang:en" # le pasamos el tema y el idioma en inglés
                }
            },
            "aggs": {
                 "Most significant users": {
                     "significant_terms": {
                       "field": "text",
                       "size": 14,  # irá variando a medida que vayamos analizando los resultados
                       "jlh": {} # en este caso, hemos escogido esta métrica
                     }
                 }
            }
        }
    )

    resultadosGnd = [] # vamos añadiendo los términos significativos con dicha métrica
    infoGnd = "(cristiano ronaldo)"
    for elem in results["aggregations"]["Most significant users"]["buckets"]:
        if (str(elem["key"]) not in resultadosGnd):
            resultadosGnd.append(str(elem["key"]))
            infoGnd = infoGnd + " OR (" + str(elem["key"]) + ")" # añadimos el término a la cadena para escanearlo
    print(infoGnd)

    resultadosJlh = [] # vamos añadiendo los términos significativos con dicha métrica
    infoJlh = "(cristiano ronaldo)"
    for elem in results_2["aggregations"]["Most significant users"]["buckets"]:
        if (str(elem["key"]) not in resultadosJlh):
            resultadosJlh.append(str(elem["key"]))
            infoJlh = infoJlh + " OR (" + str(elem["key"]) + ")" # añadimos el término a la cadena para escanearlo
    print(infoJlh)

    # primer escaneo
    escaneo = helpers.scan(es,
        index="tweets-miguel-2",
        query={
            "query" : {
                "query_string" : {
                    "query": infoGnd, # le pasamos los datos para expandir la consulta
                    "default_operator" : "AND" # los espacios en blanco sino los interpreta como operadores OR
                }
            }
        }
    )

    # segundo escaneo
    escaneo_2 = helpers.scan(es,
        index="tweets-miguel-2",
        query={
            "query" : {
                "query_string" : {
                    "query": infoJlh, # le pasamos los datos para expandir la consulta
                    "default_operator" : "AND" # los espacios en blanco sino los interpreta como operadores OR
                }
            }
        }
    )

    # generamos dos volcados, uno para cada consulta

    # abrimos el primer fichero
    file=open("terminosSignificativosGnd.tsv","w") # creamos un fichero tsv donde representaremos el siguiente formato: autor - fecha de creación - texto del tuit

    # para cada resultado guardamos el autor, la fecha de creación y el tuit
    for hit in escaneo:
        fecha = str(hit["_source"]["created_at"])
        autor = str(hit["_source"]["user_id_str"])
        tuit = str(hit["_source"]["text"])
        file.write(fecha + "\t" + autor + "\t" + tuit + "\n")

    file.close() # cerramos el fichero

    # abrimos el segundo fichero
    file2=open("terminosSignificativosJlh.tsv","w") # creamos un fichero tsv donde representaremos el siguiente formato: autor - fecha de creación - texto del tuit

    # para cada resultado guardamos el autor, la fecha de creación y el tuit
    for hit in escaneo_2:
        fecha = str(hit["_source"]["created_at"])
        autor = str(hit["_source"]["user_id_str"])
        tuit = str(hit["_source"]["text"])
        file2.write(fecha + "\t" + autor + "\t" + tuit + "\n")

    file2.close() # cerramos el fichero



if __name__ == '__main__':
    main()
