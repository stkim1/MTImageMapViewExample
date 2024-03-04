package stkim1.view.examples.mtimagemapview

import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test
import stkim1.view.geom.MTPoint
import stkim1.view.geom.MTPolygon

class PolygonUnmarshallTest {
    @OptIn(ExperimentalStdlibApi::class)
    @Test
    fun check_jsonUnmarshal() {
        // three json polygons
        // 1) a rectangle with id 4
        // 2) a triangle with id "triangle"
        // 3) a parallelogram without an id
        val str = "[{\"id\":4,\"vertices\":[[0.0,0.0],[10.0,0.0],[10,10],[0.0,10.0]]},{\"id\":\"triangle\",\"vertices\":[[0.0,0.0],[10.0,0.0],[5.5,5.5]]},{\"vertices\":[[0,0],[10,0],[15,10],[5,10]]}]"
        val moshi = Moshi.Builder()
            .addLast(PolygonAdapter())
            .build()

        val polygons: List<MTPolygon> = moshi.adapter<List<MTPolygon>>().fromJson(str) as List<MTPolygon>
        assertNotNull(polygons)
        assertEquals(polygons.size, 3)

        assertEquals(polygons[0].polygonId, 4.0)
        assertTrue(polygons[0].isPointInPolygon(
            MTPoint(
                5.0,
                5.0
            )
        ))
        assertFalse(polygons[0].isPointInPolygon(
            MTPoint(
                5.0,
                20.0
            )
        ))

        assertEquals(polygons[1].polygonId, "triangle")
        assertTrue(polygons[1].isPointInPolygon(
            MTPoint(
                2.5,
                2.5
            )
        ))
        assertFalse(polygons[1].isPointInPolygon(
            MTPoint(
                2.5,
                20.0
            )
        ))

        assertNull(polygons[2].polygonId)
        assertTrue(polygons[2].isPointInPolygon(
            MTPoint(
                10.0,
                2.5
            )
        ))
        assertFalse(polygons[2].isPointInPolygon(
            MTPoint(
                10.0,
                20.0
            )
        ))
    }
}