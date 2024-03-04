package stkim1.view.examples.mtimagemapview

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.MotionEvent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import stkim1.view.MTImageMapView.MTImageMapTouch
import stkim1.view.MTImageMapView.MTImageMapView
import stkim1.view.examples.mtimagemapview.databinding.ActivityMainBinding
import stkim1.view.geom.MTPoint
import stkim1.view.geom.MTPolygon

class MainActivity : AppCompatActivity(), MTImageMapTouch {

    private lateinit var binding: ActivityMainBinding

    @OptIn(ExperimentalStdlibApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val jsonMaps : String = assets.open("us_states.json")
            .bufferedReader()
            .use { it.readText() }
        val moshi = Moshi.Builder()
            .add(PolygonAdapter())
            .build()
        val mapList: List<MTPolygon> = moshi.adapter<List<MTPolygon>>()
            .fromJson(jsonMaps) as List<MTPolygon>
        val usDrawable : Drawable? = applicationContext.let { AppCompatResources.getDrawable(it, R.drawable.us_states) }

        val mapView = binding.root.findViewById<MTImageMapView>(R.id.image_map_view)
        mapView.setImageDrawable(usDrawable)
        mapView.setTouchedMapReceiver(this)
        mapView.setPathVisible(true)
        mapView.setPolygons(mapList)

        binding.flipVisibility.setOnClickListener { _ ->
            if (mapView.isPathVisible) {
                mapView.setPathVisible(false)
            } else {
                mapView.setPathVisible(true)
            }
            mapView.invalidate()
        }
    }

    override fun onDestroy() {
        val mapView = binding.root.findViewById<MTImageMapView>(R.id.image_map_view)
        mapView.setTouchedMapReceiver(null)

        super.onDestroy()
    }

    override fun onImageMapClicked(event: MotionEvent, point: MTPoint, polygons: List<MTPolygon>) {
        if (polygons.isNotEmpty()) {
            Toast.makeText(applicationContext, polygons.first().polygonId.toString(), Toast.LENGTH_SHORT)
                .show()
        }
    }
}