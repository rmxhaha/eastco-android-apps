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
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentOrders.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentOrders#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentOrders extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String token;
    private OnFragmentInteractionListener mListener;
    private OngoingOrder ongoingOrder;
    private ListView orderListView;

    public FragmentOrders() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentOrders.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentOrders newInstance(String param1, String param2) {
        FragmentOrders fragment = new FragmentOrders();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ongoing_orders, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //Get token
        SharedPreferences sharedPref = getActivity().getSharedPreferences("default", Context.MODE_PRIVATE);
        token = sharedPref.getString("UID", "null");
        Log.d("token ", token);

        ongoingOrder = getOngoingOrders();
        orderListView = (ListView) getActivity().findViewById(R.id.ongoingOrderListView);
        OngoingOrderAdapter adapter = new OngoingOrderAdapter(getActivity(),ongoingOrder.getTransactions());

        orderListView.setAdapter(adapter);

        Spinner orderTypeSpinner = (Spinner) getActivity().findViewById(R.id.order_spinner);

        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.order_type, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_layout);
        orderTypeSpinner.setAdapter(spinnerAdapter);

    }

    public class OngoingOrderAdapter extends BaseAdapter {
        private Context context;
        private List<Order> orders;

        public OngoingOrderAdapter(Context context, List<Order> orders) {
            this.context = context;
            this.orders = orders;
        }



        @Override
        public int getCount() {
            return orders.size();
        }

        @Override
        public Object getItem(int i) {
            return orders.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if( view == null ){
                view = LayoutInflater.from(context).inflate(R.layout.listview_tenant_order, viewGroup, false);
            }

            Order cOrder = (Order) getItem(i);

            TextView orderId = (TextView) view.findViewById(R.id.order_id);
            TextView orderDescription = (TextView) view.findViewById(R.id.order_description);

            int order_count = 0;
            for(OrderDetail dtl:cOrder.getDetails()){
                order_count += dtl.getAmount();
            }


            orderId.setText("Order No. " + cOrder.getId());
            orderDescription.setText(
                    "Ordered By         : " + cOrder.getOrderer().getUsername() + "\n" +
                    "Contact            : " + cOrder.getOrderer().getPhonenumber() + "\n" +
                    "Total Price        : " + cOrder.getTotal_price() + "\n" +
                    "Total Item Ordered : " + order_count + "\n" +
                    "Click to see more details"
            );

            return  view;
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

    OngoingOrder getOngoingOrders(){
        
        OngoingOrder ongoingOrder = new OngoingOrder();

        List<Order> transactions = new ArrayList<>();
        Order order = new Order();
        order.setAddress_description("My Address Description");

        Address address = new Address();
        address.setName("ITB hahaha");
        address.setId(333);
        order.setAddress(address);

        List<OrderDetail> details;
        details = new ArrayList<>();

        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setAmount(30);
        orderDetail.setDescription("hehe");
        Menu menu = new Menu();
        menu.setDescription("enak");
        menu.setName("ayam krikuk");
        menu.setPrice(5000);
        orderDetail.setMenu(menu);
        orderDetail.setMenu_price(4444);

        details.add(orderDetail);
        order.setDetails(details);

        User orderer = new User();
        orderer.setUsername("XXX");
        orderer.setPhonenumber("343432423432");
        order.setOrderer(orderer);

        transactions.add(order);
        ongoingOrder.setTransactions(transactions);

        return ongoingOrder;
        /*
        RequestQueue queue = Volley.newRequestQueue(getActivity().getBaseContext());
        String url = "http://contraffee.ml/api/v1/tenant/profile?api_token=" + token;

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
        */
    }
}
