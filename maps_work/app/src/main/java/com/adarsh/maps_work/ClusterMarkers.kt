package com.adarsh.maps_work

import android.content.Context
import android.graphics.Bitmap
import android.view.ViewGroup
import android.widget.ImageView
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.Cluster
import com.google.maps.android.clustering.ClusterItem
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer
import com.google.maps.android.ui.IconGenerator

public class ClusterMarkers(private val position: LatLng, private val title: String ,private val snippets: String ,
                              val iconPicture: Int)
    : ClusterItem {
    override fun getSnippet(): String? = snippets
    override fun getTitle(): String? = title
    override fun getPosition(): LatLng = position
}


public class MyClusterManagerRender(
    context: Context?,
    map: GoogleMap?,
    clusterManager: ClusterManager<ClusterMarkers>?
) : DefaultClusterRenderer<ClusterMarkers>(context, map, clusterManager) {
    private var iconGenerator:IconGenerator = IconGenerator(context?.applicationContext)
    private var imageView = ImageView(context?.applicationContext)

    init {
        val param = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        imageView.layoutParams = param
        imageView.layoutParams.height = 90
        imageView.layoutParams.width = 90
        imageView.scaleType = ImageView.ScaleType.FIT_XY

        val padding = context?.resources?.getDimension(R.dimen.custom_marker_padding)?.toInt()
        padding?.let { imageView.setPadding(padding, padding, padding, it) }

        iconGenerator.setContentView(imageView)
    }


    override fun onBeforeClusterItemRendered(item: ClusterMarkers, markerOptions: MarkerOptions) {
        super.onBeforeClusterItemRendered(item, markerOptions)
        imageView.setImageResource(item.iconPicture)
        var icon: Bitmap = iconGenerator.makeIcon()
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon)).title(item.title)
    }

    override fun shouldRenderAsCluster(cluster: Cluster<ClusterMarkers>): Boolean = false
}