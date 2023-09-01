package se.alipsa.groovy.matrixjson

import se.alipsa.groovy.matrix.Matrix
import groovy.json.JsonSlurper

class JsonImporter {


  /**
   *
   * @param str a json string containing a list of rows to import
   *        a row is defined as a json object
   * @return
   */
  Matrix parse(String str) {
    JsonSlurper importer = new JsonSlurper()
    def o = importer.parseText(str)
    if (! o instanceof List) {
      throw new IllegalArgumentException('The Json string is not a list of objects')
    }
    def rows = o as List<Map<String, List<?>>>
    Map<String, List<?>> columnMap = new LinkedHashMap<>()
    for (Map<String, ?> map in rows) {
      map.each {
        columnMap.computeIfAbsent(it.key, k -> []) << it.value
      }
    }
    return Matrix.create(columnMap)
  }
}