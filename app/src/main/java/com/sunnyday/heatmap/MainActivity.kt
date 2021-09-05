package com.sunnyday.heatmap

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.amap.api.maps.AMap
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.model.HeatMapLayerOptions
import com.amap.api.maps.model.HeatmapTileProvider
import com.amap.api.maps.model.LatLng
import com.amap.api.maps.model.TileOverlayOptions
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "MainActivity"
        const val SIZE = 500
    }

    override fun onResume() {
        super.onResume()
        mMapView.onResume()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mMapView.onCreate(savedInstanceState)

        val aMap = mMapView.map
        val latLng = LatLng(31.23,121.47) //shanghai
        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,9F))
        generateHeatMap(aMap = aMap)
    }

    override fun onPause() {
        super.onPause()
        mMapView.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mMapView.onSaveInstanceState(outState)
    }

    override fun onDestroy() {
        super.onDestroy()
        mMapView.onDestroy()
    }

    /**
     * generate heat map
     * */
    private fun generateHeatMap(aMap: AMap) {

        //1:第一步，组织热力图数据
        val list = ArrayList<LatLng>()
        val defaultX =  31.23
        val defaultY = 121.47

        repeat(SIZE) {
            Log.d(TAG,"index:$it")
            val x = Math.random() * 0.5 - 0.25
            val y = Math.random() * 0.5 - 0.25
            list.add(LatLng(defaultX+x,defaultY+y))
        }

        //2:构建热力图 HeatMapTileProvider
        val heatMapTileProvider = HeatmapTileProvider.Builder()
            .data(list)
            .gradient(HeatMapLayerOptions.DEFAULT_GRADIENT)
            .build()

        //3:绘制热力图图层
        val tileOverlayOptions = TileOverlayOptions() // 初始化 TileOverlayOptions
        tileOverlayOptions.tileProvider(heatMapTileProvider)// 设置瓦片图层的提供者
        aMap.addTileOverlay(tileOverlayOptions)// 向地图上添加 TileOverlayOptions 类对象
    }
}