package com.example.korn.lab8;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    public RecyclerView mRecycle;
    public List<Student> studentList;
    public String URL = "http://10.51.24.125/ass7mobile/select.php";
    public String URL_UP = "http://10.51.24.125/ass7mobile/update.php";
    EditText etId,etName,etTel,etEmail;
    Button btsave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecycle = findViewById(R.id.Hello);
        mRecycle.setHasFixedSize(true);
        mRecycle.setLayoutManager(new LinearLayoutManager(this));
        studentList = new ArrayList<>();

        etId = findViewById(R.id.etid);
        etName = findViewById(R.id.etname);
        etTel = findViewById(R.id.ettel);
        etEmail = findViewById(R.id.etemail);
        btsave = findViewById(R.id.btadd);
        mRecycle.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(),
                mRecycle, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view,final int position) {
                Student st = studentList.get(position);

                final AlertDialog.Builder stalog = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater inflater = getLayoutInflater();

               View view2 = inflater.inflate(R.layout.dialogupdate,null);
               stalog.setView(view2);
               final TextView id = view2.findViewById(R.id.id);
               final EditText name = view2.findViewById(R.id.name);
               final EditText tel = view2.findViewById(R.id.Tel);
                final EditText email = view2.findViewById(R.id.emailup);

               id.setText(st.getId());
                name.setText(st.getName());
                tel.setText(st.getTel());
                email.setText(st.getStd_email());

                stalog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                   public void onClick(DialogInterface dialog, int which) {
                        Update(id.getText().toString(),name.getText().toString(),tel.getText().toString(),email.getText().toString());
                        Toast.makeText(getApplicationContext(),"",Toast.LENGTH_SHORT).show();
                  }
                });

                stalog.setNegativeButton("cancle", new DialogInterface.OnClickListener() {
                   @Override
                    public void onClick(DialogInterface dialog, int which) {
                   }
                });

               stalog.show();



            }

            @Override
            public void onLongClick(View view, final int position) {
               final AlertDialog.Builder daialogDel = new AlertDialog.Builder(MainActivity.this);
               daialogDel.setMessage("คุณต้องการที่จะลบหรือไม่?");
               daialogDel.setPositiveButton("ลบ", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       Student st = studentList.get(position);
                       Del(st.getId().toString());
                   }
               });
              daialogDel.setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {

                   }
               });
                daialogDel.show();
            }
        }));

                btsave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Up();
                    }
                });
        showstudent();
    }
    public void showstudent(){
        String url = "http://10.51.24.125/ass7mobile/select.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                studentList.clear();
                try {
                    JSONArray array = new JSONArray(response);
                    for(int i = 0;i<array.length();i++){
                        JSONObject kn = array.getJSONObject(i);
                        studentList.add(new Student(
                                kn.getString("Id_Student"),
                                kn.getString("Name"),
                                kn.getString("Tel"),
                                kn.getString("Email")
                        ));
                        StudentAdapter kk = new StudentAdapter(getApplicationContext(),studentList);
                        mRecycle.setAdapter(kk);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param = new HashMap<>();
                param.put("","");
                return param;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    public void Up(){
        String url_up = "http://10.51.24.125/ass7mobile/insert.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                url_up, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            if(response.equalsIgnoreCase("success")){
                studentList.clear();
                showstudent();
            }else{
                Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
            }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param =  new HashMap<>();
                param.put("id",etId.getText().toString());
                param.put("name",etName.getText().toString());
                param.put("tel",etTel.getText().toString());
                param.put("mail",etEmail.getText().toString());
                return param;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void Update(final String id,final String name,final String tel,final String email){
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                URL_UP, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equalsIgnoreCase("success")){
                    studentList.clear();
                    showstudent();
                    Toast.makeText(getApplicationContext(),"Update success",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param =  new HashMap<>();
                param.put("id",id);
                param.put("name",name);
                param.put("tel",tel);
                param.put("email",email);
                return param;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    public void Del(final String id){
        String url_del = "http://10.51.24.125/ass7mobile/delete.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                url_del, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equalsIgnoreCase("success")){
                    studentList.clear();
                    showstudent();
                    Toast.makeText(getApplicationContext(),"Delete success",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param =  new HashMap<>();
                param.put("id",id);
                return param;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}

