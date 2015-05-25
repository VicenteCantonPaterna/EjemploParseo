package programados.ejemploparseo;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class MainActivity extends ActionBarActivity {

    private WebView webView;
    private Button btnParseo;
    private Document doc; //Contendrá la pagina web en HTML
    private String[] ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = (WebView)findViewById(R.id.webView);
        btnParseo = (Button)findViewById(R.id.button);

        btnParseo.setOnClickListener(new View.OnClickListener() {
                                 @Override
                                 public void onClick(View view) {
                                    obtenerDatos();
                                 }
                             });

        //Permite al webview ejecutar javascript
        webView.getSettings().setJavaScriptEnabled(true);

        //Añade al WebView una interfaz para javascript
        webView.addJavascriptInterface(new MyJavaScriptInterface(this.getBaseContext()), "HtmlViewer");

        //Se carga la pagina web inicial. Podría solicitarse al usuario
        webView.loadUrl("http://www.google.com");

        //Evita que se abra el navegador al pulsar un enlace dentro del webview
        webView.setWebViewClient(new WebViewClient(){

            //Se obtiene el codigo HTML una vez cargada la pagina
            @Override
            public void onPageFinished(WebView view, String url) {
                webView.loadUrl("javascript:window.HtmlViewer.showHTML" +
                        "('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>');");
            }
        });
    }


    //metodo que obtiene los links que haya en la pagina
    private void obtenerDatos() {

        Elements links = doc.select("a");
        ref = new String[links.size()];
        for (int i = 0; i < links.size();i++){
            ref[i] = links.get(i).attr("href").toString();

        }

        //se le pasa a la nueva actividad el vector de strings con las referencias
        Bundle bundle = new Bundle();
        bundle.putStringArray("ref", ref);
        Intent intent = new Intent(MainActivity.this,ReferenciasActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //Interfaz de java script que se ejecuta en el webview
    private class MyJavaScriptInterface{

        private Context ctx;

        MyJavaScriptInterface(Context ctx) {
            this.ctx = ctx;
        }

        @JavascriptInterface
        public void showHTML(String html) {
            doc  = Jsoup.parse(html);  //La llamada a parse se encarga de incluir el string a tipo document

        }
    }
}
