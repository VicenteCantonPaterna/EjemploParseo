package programados.ejemploparseo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Vicente on 21/05/2015.
 */
public class ReferenciasActivity extends Activity {

    private ArrayAdapter adapter;
    private ListView lv;
    private ArrayList<String> list = new ArrayList<>();
    private Button btnVolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_referencias);

        lv = (ListView)findViewById(R.id.listview);
        btnVolver = (Button)findViewById(R.id.btnVolver);

        Bundle extras = getIntent().getExtras();
        String[] referencias = extras.getStringArray("ref");
        for (int i = 0; i < referencias.length;i++){
            list.add(referencias[i]);
        }

        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);
        lv.setAdapter(adapter);

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReferenciasActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
