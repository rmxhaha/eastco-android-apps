package mj.eastco;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentTenant.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentTenant#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentTenant extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    //EditText
    TextView nameEdit;
    TextView descriptionEdit;
    Button editBtn;

    //Token
    String token;

    private OnFragmentInteractionListener mListener;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //Get token
        SharedPreferences sharedPref = this.getActivity().getSharedPreferences("default", Context.MODE_PRIVATE);
        token = sharedPref.getString("UID", "null");
        Log.d("token ", token);

        getTenantDescription();
        nameEdit = (TextView) getActivity().findViewById(R.id.NameEdit);
        descriptionEdit = (TextView) getActivity().findViewById(R.id.DescriptionEdit);
        editBtn = (Button) getActivity().findViewById(R.id.editButton);

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                FragmentEditTenant fragmentEditTenant = new FragmentEditTenant();
                ft.replace(R.id.Frame_Container, fragmentEditTenant);
                ft.commit();
            }
        });

    }

    public void getTenantDescription() {
        //Initiate Request
        RequestQueue queue = Volley.newRequestQueue(getActivity().getBaseContext());
        String url = "http://contraffee.ml/api/v1/tenant/profile?api_token=" + token;
        Log.d("UID ", url);

        //GET Method
        StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        JSONObject responseObject = null;

                        try {
                            responseObject = new JSONObject(response);
                            Log.d("Response", response);
                            //Edit Text
                            nameEdit.setText(responseObject.get("tenant_name").toString());
                            descriptionEdit.setText(responseObject.get("description").toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.d("ERROR","error => "+error);
                        Toast.makeText(getActivity().getBaseContext(), "get description failed", Toast.LENGTH_LONG).show();
                    }
                }

        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Accept", "application/json");
                params.put("Content-Type", "application/json");
                return params;
            }
        };
        queue.add(postRequest);
    }

    public FragmentTenant() {
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentTenant.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentTenant newInstance(String param1, String param2) {
        FragmentTenant fragment = new FragmentTenant();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragment_tenant, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
