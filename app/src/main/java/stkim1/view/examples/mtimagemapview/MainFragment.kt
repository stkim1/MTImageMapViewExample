package stkim1.view.examples.mtimagemapview

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import stkim1.view.MTImageMapView.MTImageMapTouch
import stkim1.view.MTImageMapView.MTImageMapView
import stkim1.view.geom.MTPoint
import stkim1.view.geom.MTPolygon

class MainFragment : Fragment(), MTImageMapTouch {

    @OptIn(ExperimentalStdlibApi::class)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view : View? = super.onCreateView(inflater, container, savedInstanceState)

        val jsonMaps : String = activity?.assets?.open("us_states.json")?.bufferedReader()
            .use { it?.readText()!! }
        val moshi = Moshi.Builder()
            .add(PolygonAdapter())
            .build()
        val mapList: List<MTPolygon> = moshi.adapter<List<MTPolygon>>()
            .fromJson(jsonMaps) as List<MTPolygon>
        val usState : Drawable? =
            activity?.applicationContext?.let { AppCompatResources.getDrawable(it, R.drawable.us_states) }

        val mapView : MTImageMapView? = view?.findViewById(R.id.image_map_view)
        mapView?.setImageDrawable(usState)
        mapView?.setTouchedMapReceiver(this)
        mapView?.setShowPath(true)
        mapView?.setPolygons(mapList)

        return view
    }

    override fun onDestroyView() {
        val mapView : MTImageMapView? = view?.findViewById(R.id.image_map_view)
        mapView?.setTouchedMapReceiver(null)
        super.onDestroyView()
    }

    override fun onImageMapClicked(event: MotionEvent, point: MTPoint, polygons: List<MTPolygon>) {
        if (polygons.isNotEmpty()) {
            Toast.makeText(activity?.applicationContext, polygons.first().polygonId.toString(), Toast.LENGTH_SHORT)
                .show()
        }
    }
}