# matrix-json
Json import and export functionality to and from a Matrix or Grid


## Export a Matrix to Json

The simplest way is to just use the toJason method on th JsonExporter, e.g:

```groovy
import se.alipsa.groovy.matrix.Matrix
import se.alipsa.groovy.matrixjson.JsonExporter
import java.time.LocalDate
import static se.alipsa.groovy.matrix.ListConverter.toLocalDates
import groovy.json.JsonOutput

def empData = Matrix.create(
        emp_id: 1..3,
        emp_name: ["Rick","Dan","Michelle"],
        salary: [623.3,515.2,611.0],
        start_date: toLocalDates("2012-01-01", "2013-09-23", "2014-11-15"),
        [int, String, Number, LocalDate]
)

def exporter = new JsonExporter(empData)
println JsonOutput.prettyPrint(exporter.toJson())
```
will output
```json
[
    {
        "emp_id": 1,
        "emp_name": "Rick",
        "salary": 623.3,
        "start_date": "2012-01-01"
    },
    {
        "emp_id": 2,
        "emp_name": "Dan",
        "salary": 515.2,
        "start_date": "2013-09-23"
    },
    {
        "emp_id": 3,
        "emp_name": "Michelle",
        "salary": 611.0,
        "start_date": "2014-11-15"
    }
]
```

Sometimes you need to convert the Matrix data in some way. You can do that by supplying a Map of 
Closures for each column name that should be treated. E.g:

```groovy
import se.alipsa.groovy.matrix.Matrix
import se.alipsa.groovy.matrixjson.JsonExporter
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import static se.alipsa.groovy.matrix.ListConverter.toLocalDates
import groovy.json.JsonOutput

DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern('yy/dd/MM')
def json = exporter.toJson([
        'salary': {it * 10 + ' kr'}, 
        'start_date': {dateTimeFormatter.format(it)}
])
println JsonOutput.prettyPrint(json)
```

which will result in the following
```json
[
    {
        "emp_id": 1,
        "emp_name": "Rick",
        "salary": "6233.0 kr",
        "start_date": "12/01/01"
    },
    {
        "emp_id": 2,
        "emp_name": "Dan",
        "salary": "5152.0 kr",
        "start_date": "13/23/09"
    },
    {
        "emp_id": 3,
        "emp_name": "Michelle",
        "salary": "6110.0 kr",
        "start_date": "14/15/11"
    }
]
```

