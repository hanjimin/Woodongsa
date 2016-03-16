package com.example.sec.woodongsa;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Base64;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;



public class RegisterActivity_Consultant extends Activity {

    EditText edit_register_check_passwd, edit_register_passwd;
    EditText edit_register_email,edit_register_id;
    TextView text_register_name, text_register_tel, text_register_sex;
    Button btn_register_register, btn_register_id;
    SharedPreferences login_mode;
    String myJSON;
    JSONArray sub1_list = null;

    Spinner spin_si, spin_gu, spin_corp;
    String[] corps = {"소속선택","교보생명", "농협생명", "동부화재","롯데손해보험", "메리츠화재", "메트라이프", "미래에셋생명","삼성생명","삼성화재",
            "신한생명","알리안츠생명", "프루덴셜","한화생명","한화손보","현대해상","흥국생명","흥국화재","AIA생명","ING생명","KB손해보험","MG손해보험","PCA생명"  };
    String[] corps_eng = {"all","kyobo_s","nonghyup_s","dongbu_f","lotte_f","meritz_f","merit_f","mirae_s","samsung_s","samsung_f",
            "shinhan_s","allianz_s","prudential_f","hanwha_s","hanhwa_f","hyundai_f","heungkuk_s","heungkuk_f","AIA_s","ING_s","KB_f","MG_s","pca_s"};
    String[] sis = {"서울", "부산", "인천", "대구", "울산", "광주", "대전", "강원",
            "전남", "전북", "충남", "충북", "경북", "경남", "경기", "세종", "제주"};
    String[] seoul = {"강남구", "강동구", "강북구", "강서구", "관악구", "광진구",
            "구로구", "금천구", "노원구", "도봉구", "동대문구", "동작구", "마포구",
            "서대문구", "서초구", "성동구", "성북구", "송파구", "양천구", "영등포구",
            "용산구", "은평구", "종로구", "중구", "중랑구"};
    String[] pusan = {"강서구", "금정구", "기장군", "남구", "동구",
            "동래구", "부산진구", "북구", "사상구", "사하구", "서구", "수영구",
            "연제구", "영도구", "중구", "해운대구"};
    String[] incheon = {"강화군", "계양구", "남구", "남동구",
            "동구", "부평구", "서구", "연수구", "옹진군", "중구"};
    String[] daegu = {"남구", "달서구", "달성군", "동구", "북구", "서구", "수성구", "중구"};
    String[] ulsan = {"남구", "동구", "북구", "울주군", "중구"};
    String[] kwangju = {"광산군", "남구", "동구", "북구", "서구"};
    String[] daejun = {"대덕구", "동구", "서구", "유성구", "중구"};
    String[] kangwon = {"강릉시", "고성군", "동해시", "삼척시", "속초시",
            "양구군", "양양군", "영월군", "원주시", "인제군", "정선군", "철원군",
            "춘천시", "태백시", "평창군", "홍천군", "화천군", "횡성군"};
    String[] junnam = {"강지군", "고흥군", "곡성군", "광양시", "구례군",
            "나주시", "담양군", "목포시", "무안군", "보성군", "순천시", "신안군", "여수시",
            "영광군", "영암군", "완도군", "장성군", "장흥군", "진도군", "함평군", "해남군", "화순군"};
    String[] junbuk = {"고창군", "군산시", "김제시", "남원시", "무주군",
            "부안군", "순창군", "완주군", "익산시", "임실군", "장수군", "전주시", "정읍시", "진안군"};
    String[] chungnam = {"계룡시", "공주시", "금산군", "논산시", "당진시",
            "보령시", "부여군", "서산시", "서천군", "아산시", "연기군", "예산군", "천안시", "청양군", "태안군", "홍성군"};
    String[] chungbuk = {"괴산군", "단양군", "보은군", "영동군", "옥천군",
            "음성군", "제천시", "증평군", "진천군", "청원군", "청주시", "충주시"};
    String[] kyungbuk = {"경산시", "경주시", "고령군", "구미시", "군위군",
            "김천시", "문경시", "봉화군", "상주시", "성주군", "안동시", "영덕군", "영양군",
            "영주시", "영천시", "예천군", "울릉군", "울진군", "의성군", "청도군", "청송군", "칠곡군", "포항시"};
    String[] kyungnam = {"거제시", "거창군", "고성군", "김해시", "남해군",
            "밀양시", "사천시", "산청군", "양산시", "의령군", "진주시", "창녕군",
            "창원시", "통영시", "하동군", "함안군", "함양군", "합천군"};
    String[] kyunggi = {"가평군", "고양시", "과천시", "광명시", "광주시",
            "구리시", "군포시", "남양주시", "동두천시", "부천시", "성남시", "수원시",
            "시흥시", "안산시", "안성시", "안양시", "양주시", "양평군", "여주시", "연천군",
            "오산시", "용인시", "의왕시", "의정부시", "이천시", "파주시", "평택시", "포천시", "하남시", "화성시"};
    String[] sejong = {"전체"};
    String[] jeju = {"서귀포시", "제주시"};

    String[] sex = {"남자", "여자"};
    int id_check = 0;

    String phoneNo, name, gender, birthday;
    EditText edit_register_work_year, edit_register_code_num, edit_register_introduce;

    ProgressDialog prgDialog;
    String encodedString;
    RequestParams params = new RequestParams();
    String imgPath, fileName;
    Bitmap bitmap;
    private static int RESULT_LOAD_IMG = 1;
    int upload_result = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_activity__consultant);

        upload_result = 0;

        ActionBarInit();

        prgDialog = new ProgressDialog(this);
        prgDialog.setCancelable(false);

        id_check = 0;

        edit_register_check_passwd = (EditText) findViewById(R.id.edit_register_check_passwd);
        edit_register_passwd = (EditText) findViewById(R.id.edit_register_passwd);
        edit_register_email = (EditText) findViewById(R.id.edit_register_email);
        edit_register_work_year = (EditText) findViewById(R.id.edit_register_work_year);
        edit_register_code_num = (EditText) findViewById(R.id.edit_register_code_num);
        edit_register_id = (EditText) findViewById(R.id.edit_register_id);
        edit_register_id.setFilters(new InputFilter[]{filterAlphaNum});
        edit_register_introduce = (EditText) findViewById(R.id.edit_register_introduce);

        text_register_name = (TextView) findViewById(R.id.text_register_name);
        text_register_sex = (TextView) findViewById(R.id.text_register_sex);
        text_register_tel = (TextView) findViewById(R.id.text_register_tel);


        spin_si = (Spinner) findViewById(R.id.spinner_register_si);
        spin_gu = (Spinner) findViewById(R.id.spinner_register_gu);
        spin_corp = (Spinner) findViewById(R.id.spinner_register_corp);

        btn_register_register = (Button) findViewById(R.id.btn_register_register);
        btn_register_id = (Button) findViewById(R.id.btn_register_checkid);



        Intent intent = getIntent();

        phoneNo = intent.getStringExtra("phoneNo");
        name = intent.getStringExtra("name");
        gender = intent.getStringExtra("gender");
        birthday = intent.getStringExtra("birthday");

        text_register_name.setText(name);
        text_register_tel.setText(phoneNo);
        if(gender.equals("0")){
            text_register_sex.setText(sex[0]);
        } else {
            text_register_sex.setText(sex[1]);
        }

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item,sis);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_si.setAdapter(adapter1);

        spin_si.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(Color.GRAY);
                if (position == 0) {
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, seoul);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin_gu.setAdapter(adapter2);
                } else if (position == 1) {
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, pusan);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin_gu.setAdapter(adapter2);
                } else if (position == 2) {
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, incheon);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin_gu.setAdapter(adapter2);
                } else if (position == 3) {
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, daegu);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin_gu.setAdapter(adapter2);
                } else if (position == 4) {
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, ulsan);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin_gu.setAdapter(adapter2);
                } else if (position == 5) {
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, kwangju);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin_gu.setAdapter(adapter2);
                } else if (position == 6) {
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, daejun);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin_gu.setAdapter(adapter2);
                } else if (position == 7) {
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, kangwon);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin_gu.setAdapter(adapter2);
                } else if (position == 8) {
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, junnam);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin_gu.setAdapter(adapter2);
                } else if (position == 9) {
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, junbuk);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin_gu.setAdapter(adapter2);
                } else if (position == 10) {
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, chungnam);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin_gu.setAdapter(adapter2);
                } else if (position == 11) {
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, chungbuk);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin_gu.setAdapter(adapter2);
                } else if (position == 12) {
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, kyungbuk);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin_gu.setAdapter(adapter2);
                } else if (position == 13) {
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, kyungnam);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin_gu.setAdapter(adapter2);
                } else if (position == 14) {
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, kyunggi);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin_gu.setAdapter(adapter2);
                } else if (position == 15) {
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, sejong);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin_gu.setAdapter(adapter2);
                } else if (position == 16) {
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, jeju);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin_gu.setAdapter(adapter2);
                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spin_gu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(Color.GRAY);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, corps);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_corp.setAdapter(adapter3);

        spin_corp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(Color.GRAY);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btn_register_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((edit_register_id.getText().toString().equals(null)) ||
                        (edit_register_id.getText().toString().length() < 5) ){
                    Toast.makeText(RegisterActivity_Consultant.this, "아이디 형식이 맞지 않습니다.", Toast.LENGTH_SHORT).show();
                    edit_register_id.setText(null);
                } else {
                    checkID(edit_register_id.getText().toString());
                }
            }
        });

        btn_register_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(id_check == 0){
                    Toast.makeText(RegisterActivity_Consultant.this, "아이디 중복확인을 해주세요",Toast.LENGTH_SHORT).show();
                } else {

                    if(upload_result == 0){
                        Toast.makeText(RegisterActivity_Consultant.this, "프로필 사진이 업로드되지 않았습니다.",Toast.LENGTH_SHORT).show();
                    } else {
                        if((edit_register_check_passwd.getText().toString().equals("")) ||
                                (edit_register_id.getText().toString().equals("")) ||
                                (edit_register_passwd.getText().toString().equals("")) ||
                                (edit_register_email.getText().toString().equals("")) ||
                                (edit_register_introduce.getText().toString().equals("")) ||
                                (edit_register_work_year.getText().toString().equals("")) ||
                                (edit_register_code_num.getText().toString().equals(""))) {
                            Toast.makeText(RegisterActivity_Consultant.this,"입력하지 않은 정보가 있습니다.",Toast.LENGTH_SHORT).show();
                        } else if(!edit_register_passwd.getText().toString().equals(edit_register_check_passwd.getText().toString())){
                            Toast.makeText(RegisterActivity_Consultant.this,"비밀번호를 다시 확인해주세요.",Toast.LENGTH_SHORT).show();
                        } else {
                            String id = edit_register_id.getText().toString();
                            String name = text_register_name.getText().toString();
                            String passwd = edit_register_passwd.getText().toString();
                            String si = spin_si.getSelectedItem().toString();
                            String gu = spin_gu.getSelectedItem().toString();
                            String tel = text_register_tel.getText().toString();
                            String sex = text_register_sex.getText().toString();
                            String email = edit_register_email.getText().toString();
                            String work_year = edit_register_work_year.getText().toString();
                            String code_num = edit_register_code_num.getText().toString();

                            int index = spin_corp.getSelectedItemPosition();

                            String corp = corps_eng[index];
                            String introduce = edit_register_introduce.getText().toString();
                            String img = id + "_" + fileName;

                            register_consultant(name,corp,sex,work_year,introduce,id,passwd,si,gu,img,tel,code_num,email,birthday);
                        }
                    }
                }


            }
        });
    }

    private void register_consultant(String name, String corp, String sex, String work_year, String introduce, String id, String passwd, String si, String gu, String img, String tel, String code_num, String email, String birthday){
        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("name", params[0]));
                nameValuePairs.add(new BasicNameValuePair("corp", params[1]));
                nameValuePairs.add(new BasicNameValuePair("sex", params[2]));
                nameValuePairs.add(new BasicNameValuePair("work_year", params[3]));
                nameValuePairs.add(new BasicNameValuePair("introduce", params[4]));
                nameValuePairs.add(new BasicNameValuePair("id", params[5]));
                nameValuePairs.add(new BasicNameValuePair("passwd", params[6]));
                nameValuePairs.add(new BasicNameValuePair("si", params[7]));
                nameValuePairs.add(new BasicNameValuePair("gu", params[8]));
                nameValuePairs.add(new BasicNameValuePair("img", params[9]));
                nameValuePairs.add(new BasicNameValuePair("tel", params[10]));
                nameValuePairs.add(new BasicNameValuePair("code_num", params[11]));
                nameValuePairs.add(new BasicNameValuePair("email", params[12]));
                nameValuePairs.add(new BasicNameValuePair("birthday", params[13]));


                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost("http://woodongsa.com/app/app_insert_consultant.php");
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8));

                    HttpResponse response = httpClient.execute(httpPost);

                    HttpEntity entity = response.getEntity();


                } catch (ClientProtocolException e) {

                } catch (IOException e) {

                }
                return "회원가입 신청 완료, 승인완료 시 연락드리겠습니다.";
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(RegisterActivity_Consultant.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(name,corp,sex,work_year,introduce,id,passwd,si,gu,img,tel,code_num,email,birthday);
    }

    public void loadImagefromGallery(View view) {

        if(id_check == 0){
            Toast.makeText(RegisterActivity_Consultant.this, "아이디를 먼저 정해주세요",Toast.LENGTH_SHORT).show();
        } else {
            // Create intent to Open Image applications like Gallery, Google Photos
            Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            // Start the Intent
            startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data

                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                // Get the cursor
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgPath = cursor.getString(columnIndex);
                cursor.close();
                ImageView imgView = (ImageView) findViewById(R.id.image_register_profile);
                // Set the Image in ImageView
                imgView.setImageBitmap(BitmapFactory
                        .decodeFile(imgPath));
                // Get the Image's file name
                String fileNameSegments[] = imgPath.split("/");
                fileName = fileNameSegments[fileNameSegments.length - 1];
                // Put file name in Async Http Post Param which will used in Php web app
                params.put("filename", fileName);
                params.put("id", edit_register_id.getText().toString());

            } else {
                Toast.makeText(this, "선택된 사진이 없습니다.",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Error!", Toast.LENGTH_LONG)
                    .show();
        }

    }

    public void uploadImage(View v) {

        if(id_check == 0){
            Toast.makeText(RegisterActivity_Consultant.this, "아이디를 먼저 정해주세요.",Toast.LENGTH_SHORT).show();
        } else {
            // When Image is selected from Gallery
            if (imgPath != null && !imgPath.isEmpty()) {
                prgDialog.setMessage("업로드 중입니다.");
                prgDialog.show();
                // Convert image to String using Base64
                encodeImagetoString();
                // When Image is not selected from Gallery
            } else {
                Toast.makeText(
                        getApplicationContext(),
                        "사진을 선택하세요.",
                        Toast.LENGTH_LONG).show();
            }
        }
    }
    public void encodeImagetoString() {
        new AsyncTask<Void, Void, String>() {

            protected void onPreExecute() {

            };

            @Override
            protected String doInBackground(Void... params) {
                BitmapFactory.Options options = null;
                options = new BitmapFactory.Options();
                options.inSampleSize = 4;
                bitmap = BitmapFactory.decodeFile(imgPath,
                        options);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                // Must compress the Image to reduce image size to make upload easy
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
                byte[] byte_arr = stream.toByteArray();
                // Encode Image to String
                encodedString = Base64.encodeToString(byte_arr, 0);
                return "";
            }

            @Override
            protected void onPostExecute(String msg) {
                prgDialog.setMessage("업로드중...");
                // Put converted Image string into Async Http Post param
                params.put("image", encodedString);
                // Trigger Image upload
                triggerImageUpload();
            }
        }.execute(null, null, null);
    }

    public void triggerImageUpload() {
        makeHTTPCall();
    }

    // http://192.168.2.4:9000/imgupload/upload_image.php
    // http://192.168.2.4:9999/ImageUploadWebApp/uploadimg.jsp
    // Make Http call to upload Image to Php server
    public void makeHTTPCall() {
        prgDialog.setMessage("uploading...");
        AsyncHttpClient client = new AsyncHttpClient();
        // Don't forget to change the IP address to your LAN address. Port no as well.
        client.post("http://woodongsa.com/img/app_upload_profile.php",
                params, new AsyncHttpResponseHandler() {
                    // When the response returned by REST has Http
                    // response code '200'
                    @Override
                    public void onSuccess(String response) {
                        // Hide Progress Dialog
                        prgDialog.hide();
                        Toast.makeText(getApplicationContext(), response,
                                Toast.LENGTH_LONG).show();
                        upload_result = 1;
                    }

                    // When the response returned by REST has Http
                    // response code other than '200' such as '404',
                    // '500' or '403' etc
                    @Override
                    public void onFailure(int statusCode, Throwable error,
                                          String content) {
                        // Hide Progress Dialog
                        prgDialog.hide();
                        upload_result = 0;
                        // When Http response code is '404'
                        if (statusCode == 404) {
                            Toast.makeText(getApplicationContext(),
                                    "Requested resource not found",
                                    Toast.LENGTH_LONG).show();
                        }
                        // When Http response code is '500'
                        else if (statusCode == 500) {
                            Toast.makeText(getApplicationContext(),
                                    "Something went wrong at server end",
                                    Toast.LENGTH_LONG).show();
                        }
                        // When Http response code other than 404, 500
                        else {
                            Toast.makeText(
                                    getApplicationContext(),
                                    /*
                                    "Error Occured \n Most Common Error: \n1. Device not connected to Internet\n2. Web App is not deployed in App server\n3. App server is not running\n HTTP Status code : "
                                    */
                                    "업로드 실패, 다른 사진을 선택해 주시거나 나중에 다시 시도해 주세요.\nError code : "
                                            + statusCode, Toast.LENGTH_LONG)
                                    .show();
                        }
                    }
                });
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        // Dismiss the progress bar when application is closed
        if (prgDialog != null) {
            prgDialog.dismiss();
        }
    }


    public InputFilter filterAlphaNum = new InputFilter() {
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            Pattern ps = Pattern.compile("^[a-zA-Z0-9]*$");
            if (!ps.matcher(source).matches()) {
                return "";
            }
            return null;
        }
    };

    protected void checkID(String id){
        class LoginAsync extends AsyncTask<String, Void, String> {
            private Dialog loadingDialog;

            @Override
            protected void onPreExecute(){
                super.onPreExecute();
                loadingDialog = ProgressDialog.show(RegisterActivity_Consultant.this, "확인 중입니다.", "Loading...");
            }

            @Override
            protected String doInBackground(String... params){
                String pass = params[0];

                InputStream is = null;
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("id", pass));
                String result = null;

                try{
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost("http://woodongsa.com/app/app_id_check_consultant.php");
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                    HttpResponse response = httpClient.execute(httpPost);

                    HttpEntity entity = response.getEntity();

                    is = entity.getContent();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
                    StringBuilder sb = new StringBuilder();

                    String line = null;
                    while ((line = reader.readLine()) != null)
                    {
                        sb.append(line + "\n");
                    }
                    result = sb.toString();
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return result;
            }
            @Override
            protected void onPostExecute(String result){
                String s = result.trim();
                loadingDialog.dismiss();
                if(s.equalsIgnoreCase("success")){
                    Toast.makeText(getApplicationContext(),"이미 존재하는 아이디 입니다.", Toast.LENGTH_SHORT).show();
                    id_check = 0;
                }else {
                    Toast.makeText(getApplicationContext(), "사용 가능 한 아이디 입니다.", Toast.LENGTH_LONG).show();
                    id_check=1;
                }
            }
        }
        LoginAsync la = new LoginAsync();
        la.execute(id);
    }

/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register_activity__consultant, menu);
        return true;
    }
*/
@Override
public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    switch (item.getItemId()) {
        case android.R.id.home:
            Intent intent = new Intent(getApplicationContext(), Select_Service_Activity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            return true;
        default:
            return super.onOptionsItemSelected(item);
    }
}

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode == KeyEvent.KEYCODE_BACK){
            Intent intent = new Intent(this, Certificate_Webview_Consultant_Activity4.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
        return false;
    }


    private void ActionBarInit(){

        getActionBar().setTitle("");
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
