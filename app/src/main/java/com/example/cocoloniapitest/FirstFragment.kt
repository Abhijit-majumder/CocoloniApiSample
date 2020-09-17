package com.example.cocoloniapitest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.fragment_first.*
import org.json.JSONObject

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.button_first).setOnClickListener {
            callApi()
        }
    }

    private fun callApi() {
        val requestQueue = Volley.newRequestQueue(this.context)
        val url = "https://api-lovefortune.zappallas.com/logicsrv2/api/v1.0/service/menus/LF_0400"
        val jsonObjectRequest = CustomJsonObjectRequestBasicAuth(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                textview_first.text = "Response : $response"
            },
            Response.ErrorListener { error ->
                // TODO: Handle error
            }
        )
        requestQueue.add(jsonObjectRequest)
    }

    // Class to make a volley json object request with basic authentication
    class CustomJsonObjectRequestBasicAuth(
        method:Int, url: String,
        jsonObject: JSONObject?,
        listener: Response.Listener<JSONObject>,
        errorListener: Response.ErrorListener,
    )
        : JsonObjectRequest(method,url, jsonObject, listener, errorListener) {


        @Throws(AuthFailureError::class)
        override fun getHeaders(): Map<String, String> {
            val headers = HashMap<String, String>()
            headers["Content-Type"] = "application/json"
            //val credentials:String = "username:password"
            val auth = "Basic"
            headers["Authorization"] = auth
            return headers
        }
    }
}