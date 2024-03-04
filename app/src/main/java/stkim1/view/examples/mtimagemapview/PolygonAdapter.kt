package stkim1.view.examples.mtimagemapview

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.ToJson
import stkim1.view.geom.MTPoint
import stkim1.view.geom.MTPolygon
import java.io.IOException

class PolygonAdapter : JsonAdapter<MTPolygon>() {

    @ToJson
    override fun toJson(p0: JsonWriter, p1: MTPolygon?) {
    }

    @Throws(IOException::class)
    fun readVertices(reader: JsonReader): List<MTPoint> {
        if (reader.peek() == JsonReader.Token.NULL)
            throw IOException("Null Vertices Input")

        val vertices: MutableList<MTPoint> = ArrayList()
        reader.beginArray()
        while (reader.hasNext()) {
            val vertex = reader.readJsonValue() as List<Double>?
            vertices.add(MTPoint(vertex!!))
        }
        reader.endArray()
        return vertices
    }

    @FromJson
    override fun fromJson(reader : JsonReader): MTPolygon {
        var polygonId: Any? = null
        var vertices: List<MTPoint>? = null

        reader.beginObject()
        while (reader.hasNext()) {
            when (reader.nextName()) {
                "id" -> polygonId = reader.readJsonValue()
                "vertices" -> vertices = readVertices(reader)
                else -> reader.skipValue()
            }
        }
        reader.endObject()

        return MTPolygon(polygonId, vertices!!)
    }
}