package mj.eastco;

import android.app.Activity;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
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
import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.google.gson.Gson;

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
    private static final String baseURL = "contraffee.ml";
    // TODO: Rename and change types of parameters
    private String token;
    private OnFragmentInteractionListener mListener;
    private OngoingOrder ongoingOrder;
    private ListView orderListView;
    private String filterBy = "waiting_for_response";

    public FragmentOrders() {
        // Required empty public constructor
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
        startUpOrdersFragment();
    }
    private void startUpOrdersFragment(){
        //Get token
        SharedPreferences sharedPref = getActivity().getSharedPreferences("default", Context.MODE_PRIVATE);
        token = sharedPref.getString("UID", "null");
        Log.d("token ", token);

        Spinner orderTypeSpinner = (Spinner) getActivity().findViewById(R.id.order_spinner);

        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.order_type, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_layout);

        orderTypeSpinner.setAdapter(spinnerAdapter);
        orderTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                // Your code here
                String rawFilterBy = Arrays.asList((getResources().getStringArray(R.array.order_type))).get(position);

                if( rawFilterBy.equals("Waiting for Response") ){
                    filterBy = "waiting_for_response";
                }
                else if( rawFilterBy.equals("Accepted") ){
                    filterBy = "processing";
                }
                else if( rawFilterBy.equals("Delivering")){
                    filterBy = "delivering";
                }
                else {
                    filterBy = "others";
                }
                Log.d("FFF", rawFilterBy);
                Log.d("FFF", filterBy);

                updateOngoingOrders();

            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        }); ;

        updateOngoingOrders();
    }
    public void renderOrderList(){
        orderListView = (ListView) getActivity().findViewById(R.id.ongoingOrderListView);
        List<Order> orders = ongoingOrder.getTransactions();

        List<Order> filteredOrders = new ArrayList<>();

        if( filterBy != "others")
            for(Order o:orders){
                if( o.getStatus().equals(filterBy) )
                    filteredOrders.add(o);
            }
        else
            filteredOrders = orders;

        OngoingOrderAdapter adapter = new OngoingOrderAdapter(getActivity(),filteredOrders);
        ((TextView) getActivity().findViewById(R.id.loading_text)).setText("");

        orderListView.setAdapter(adapter);
    }

    public void acceptDenyOrder(int order_id, String verdict){
        String url;
        url = "http://"+baseURL+"/api/v1/tenant/order/ongoing/"+order_id+"/"+verdict+"?api_token="+token;
        RequestQueue queue = Volley.newRequestQueue(getActivity().getBaseContext());

        Log.d("Response", url);
        //GET Method
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        updateOngoingOrders();
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

            final Order cOrder = (Order) getItem(i);

            TextView orderId = (TextView) view.findViewById(R.id.order_id);
            TextView addressDescription = (TextView) view.findViewById(R.id.address_description);
            TextView status = (TextView) view.findViewById(R.id.order_status);
            TextView username = (TextView) view.findViewById(R.id.username);

            username.setText(cOrder.getOrderer().getUsername());
            addressDescription.setText( "No Telp : " + cOrder.getOrderer().getPhonenumber() + "\n" + cOrder.getAddress().getName() + ",\nNote : " + cOrder.getAddress_description());

            TableLayout table = (TableLayout) view.findViewById(R.id.table_order);

            for(OrderDetail detail:cOrder.getDetails()){
                TableRow tr = new TableRow(getActivity());
                TextView foodName = new TextView(getActivity());
                TextView foodQty = new TextView(getActivity());
                TextView foodPrice = new TextView(getActivity());

                foodName.setText(detail.getMenu().getName());
                foodQty.setText(Integer.toString(detail.getAmount()));
                foodPrice.setText(Integer.toString(detail.getMenu_price()));

                tr.addView(foodName);
                tr.addView(foodQty);
                tr.addView(foodPrice);

                table.addView(tr);
            }

            TableRow tr = new TableRow(getActivity());
            TextView foodName = new TextView(getActivity());
            TextView foodQty = new TextView(getActivity());
            TextView foodPrice = new TextView(getActivity());

            foodName.setText("");
            foodQty.setText("Total");
            foodPrice.setText(Integer.toString(cOrder.getTotal_price()));

            tr.addView(foodName);
            tr.addView(foodQty);
            tr.addView(foodPrice);

            table.addView(tr);

            Button acceptBtn = (Button) view.findViewById(R.id.accept_btn);
            Button denyBtn = (Button) view.findViewById(R.id.deny_btn);
            Button cancelBtn = (Button) view.findViewById(R.id.cancel_btn);
            Button readyBtn = (Button) view.findViewById(R.id.ready_to_deliver_btn);

            acceptBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    acceptDenyOrder(cOrder.getId(), "accept");
                }
            });

            denyBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    acceptDenyOrder(cOrder.getId(), "deny");
                }
            });

            cancelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    acceptDenyOrder(cOrder.getId(), "cancel");
                }
            });

            readyBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    acceptDenyOrder(cOrder.getId(), "ready_to_deliver");
                }
            });
            if( filterBy.equals("waiting_for_response")){
                cancelBtn.setVisibility(View.GONE);
                readyBtn.setVisibility(View.GONE);
            }
            else if( filterBy.equals("processing") ){
                acceptBtn.setVisibility(View.GONE);
                denyBtn.setVisibility(View.GONE);
            }
            else {
                cancelBtn.setVisibility(View.GONE);
                acceptBtn.setVisibility(View.GONE);
                denyBtn.setVisibility(View.GONE);
                readyBtn.setVisibility(View.GONE);
            }






            orderId.setText("#" + cOrder.getId());
            status.setText(cOrder.getStatus());

            return  view;
        }
    }

    @Override
    public void onAttach(Context context) {
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

    void updateOngoingOrders(){
        ((TextView) getActivity().findViewById(R.id.loading_text)).setText("loading...");

        /*
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
        */

        String url;
        if( filterBy == "others" ){
            url = "http://"+baseURL+"/api/v1/tenant/order/history?api_token="+token;
        }
        else {
            url = "http://"+baseURL+"/api/v1/tenant/order/ongoing?api_token="+token;
        }
        RequestQueue queue = Volley.newRequestQueue(getActivity().getBaseContext());

        Log.d("Response", url);
        //GET Method
        StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response

                        ongoingOrder = new Gson().fromJson( response, OngoingOrder.class );
                        renderOrderList();
                        Log.d("Response", response);
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
}
