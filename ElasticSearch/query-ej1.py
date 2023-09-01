import json # Para poder trabajar con objetos JSON
import requests
from elasticsearch import Elasticsearch
from urllib.parse import quote

# Ejercicio 1: Índice de tuits escritos en español usando shingles de dos y tres términos

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

    # sacamos los trending topics
    results = es.search(request_timeout=4000,
        index="tweets-miguel",
        body={
            "size": 0,
            "query":{
                "query_string": {
                    "query":"lang:es"
                }
            },
            "aggs": {
                 "Trending topics per hour": {
                     "date_histogram": {
                       "field": "created_at",
                        "fixed_interval": "1h"
                     },
                     "aggs": {
                        "Trending topics": {
                            "significant_terms": {
                                "field": "text",
                                "size": 50,
                                "gnd": {}
                            }
                         }
                     }
                 }
            }
        }
    )

    # serializar información para no esperar mucho tiempo es una opción
    trendingTopics = [] # lista de trending topics ordenados por fecha y hora
    resultFinal = [] # se irá acumulando el resultado
    for elemento in results["aggregations"]["Trending topics per hour"]["buckets"]:
        fechaHora = elemento["key_as_string"] # fecha y hora del tuit
        elem = elemento["Trending topics"]["buckets"]
        for key in elem:
            trending = key["key"] # trending topic
            trendingTopics.append(trending) # lo añadimos a una lista
            resultFinal.append('fecha y hora: ' + fechaHora + ', trending topic: ' + trending) # añadimos al resultado final la fecha y hora con su trending topic

    diccionarioIds = {} # diccionario en el que la clave será el trending topic y el valor la id de la entidad
    i = 0
    for elem in trendingTopics: # si no existe la clave, se añade igualmente
        # consulta a wikidata, hacer el encoding y devuelve un json, cojo la id de la primera entidad Q...
        if (elem not in diccionarioIds.keys()): # si no la hemos buscado ya
            key = devuelvePrimeraId(elem) # entidad de wikidata
            if (key != None): resultFinal[i] = resultFinal[i] + ", entidad: " + key # tiene entidad
            else: resultFinal[i] = resultFinal[i] + ", sin entidad" # no tiene entidad
            diccionarioIds[elem] = key # creamos un elemento, con clave la key y valor una lista con el trending topic
        else:
            if (diccionarioIds[elem] != None): resultFinal[i] = resultFinal[i] + ", entidad: " + diccionarioIds[elem] # tiene entidad
            else: resultFinal[i] = resultFinal[i] + ", sin entidad" # no tiene entidad
        i = i + 1

    diccionarioTipo = {} # diccionario donde almaceno la entidad y el tipo
    i = 0
    for clave in trendingTopics:
        if (diccionarioIds[clave] != None): # no tiene entidad
            if (clave not in diccionarioTipo.keys()):
                x = diccionarioIds[clave] # entidad de wikidata
                request = 'https://www.wikidata.org/w/api.php?action=wbgetentities&ids=' + str(x) + '&languages=es&format=json' # primer json
                elem = list(requests.get(request).json()["entities"][x]["claims"]) # sacamos todas las entidades P
                value = 0
                for e in elem:
                    if (str(e) == "P31"): # si existe la P31 entonces tiene tipo
                        value = 1
                if (value == 0):
                    diccionarioTipo[clave] = "SIN TIPO" # no tiene tipo
                else:
                    value = requests.get(request).json()["entities"][x]["claims"]["P31"][0]["mainsnak"]["datavalue"]["value"]["id"] # sacamos la Q
                    requestQ = 'https://www.wikidata.org/w/api.php?action=wbgetentities&ids=' + str(value) + '&languages=es,en&format=json' # segundo json, y le pasamos la Q
                    tipo = 0
                    try:
                        tipo = str(requests.get(requestQ).json()["entities"][value]["labels"]["es"]["value"]) # obtenemos el tipo
                    except KeyError:
                        tipo = str(requests.get(requestQ).json()["entities"][value]["labels"]["en"]["value"]) # no existe la etiqueta en español, luego la selecciono en inglés
                    diccionarioTipo[clave] = tipo # añadimos el tipo
                    if (diccionarioTipo[clave]!="SIN TIPO"): # tiene que tener tipo
                        resultFinal[i] = resultFinal[i] + ", tipo: " + diccionarioTipo[clave] # acumulamos en el resultado final
            else:
                if (diccionarioTipo[clave]!="SIN TIPO"): # tiene que tener tipo
                    resultFinal[i] = resultFinal[i] + ", tipo: " + diccionarioTipo[clave] # acumulamos en el resultado final
        i = i + 1

    i = 0
    j = 0
    sinonimos = [] # para controlar que no se introduzcan sinónimos repetidos
    for trendingTopic in trendingTopics: # vamos a ir añadiendo los sinónimos en caso de que los tenga
        idEntidad = diccionarioIds[trendingTopic] # id de la entidad a buscar sinónimos
        if (idEntidad != None):
            for sinonimosTrendingTopic in trendingTopics: # recorremos la lista
                otraId = diccionarioIds[sinonimosTrendingTopic] # id del sinónimo a buscar
                if (otraId==idEntidad and trendingTopic!=sinonimosTrendingTopic): # si tiene la misma id y los términos son diferentes
                    if (sinonimosTrendingTopic not in sinonimos): # no está en la lista, luego se puede incluir como sinónimo nuevo
                        sinonimos.append(sinonimosTrendingTopic) # añadimos el sinónimo
                        if (j==0):
                            resultFinal[i] = resultFinal[i] + ", sinónimos: " + sinonimosTrendingTopic # añadimos el primer sinónimo
                            j = j + 1
                        else:
                            resultFinal[i] = resultFinal[i] + ", " + sinonimosTrendingTopic # añadimos los siguientes sinónimos
            j = 0
            sinonimos = []
        i = i + 1

    file=open("trendingTopics.txt","w") # creamos un fichero de texto donde representaremos el siguiente formato: fecha y hora - trending topic - entidad wikidata - [tipo - sinónimos]
    for elemento in resultFinal:
        file.write(elemento + "\n") # escribimos los datos ordenados por fecha y hora
    file.close() # cerramos el fichero

def devuelvePrimeraId(trendingTopic):
    request = 'https://www.wikidata.org/w/api.php?action=wbsearchentities&language=es&format=json&search=' + quote(trendingTopic)
    elem = list(requests.get(request).json()["search"])
    if (elem.count==0):
        return;
    for ids in elem:
        return ids["id"]

if __name__ == '__main__':
    main()
