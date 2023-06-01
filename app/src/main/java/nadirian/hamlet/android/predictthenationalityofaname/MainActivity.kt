package nadirian.hamlet.android.predictthenationalityofaname

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.os.Handler
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.neovisionaries.i18n.CountryCode
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.concurrent.thread


class MainActivity : AppCompatActivity() {

    private val pgsBar: ProgressBar? = null
    private var i = 0
    private val hdlr = Handler()
    lateinit var namePersonEdt: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var progressBar = findViewById<ProgressBar>(R.id.progressBar) as ProgressBar
        runProgressBar(progressBar)
        namePersonEdt = findViewById(R.id.namePersonEdt)
        val quotesApi = RetrofitHelper.getInstance().create(QuotesApi::class.java)
        if (namePersonEdt.text.toString().isNotEmpty()) {
            submitRequest(quotesApi, progressBar, namePersonEdt)
        }
        namePersonEdt.setOnClickListener {
            if (namePersonEdt.text.toString().isNotEmpty())
                submitRequest(quotesApi, progressBar, namePersonEdt)
        }
    }

    private fun convertCountryCodeToFullNameCountry(countryCode: String): String {
        println(countryCode)
        var code: CountryCode = CountryCode.getByCode(countryCode)
        return "Country: " + code.getName()
    }

    private fun submitRequest(
        quotesApi: QuotesApi,
        progressBar: ProgressBar,
        namePersonEdt: EditText
    ) {
        GlobalScope.launch {

            var inputName: String = namePersonEdt.text.toString()

            var result = quotesApi.getQuotes(inputName)

            runOnUiThread {
                progressBar.visibility = View.INVISIBLE

                val countryAndProbability = HashMap<String, String>()
                var sizeBody: Int? = result.body()?.country?.size
                if (sizeBody != null) {
                    for (i in 0 until sizeBody) {
                        var key =
                            result.body()?.country?.get(i)?.country_id.toString()
                        key = convertCountryCodeToFullNameCountry(key)
                        val value =
                            "Probability: " + result.body()?.country?.get(i)?.probability.toString()
                        countryAndProbability[key] = value
                    }
                }
                val nameList = ArrayList(countryAndProbability.keys)
                val probabilityList = ArrayList(countryAndProbability.values)
                val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
                recyclerView.layoutManager = LinearLayoutManager(applicationContext)
                recyclerView.adapter = CustomAdapter(nameList, probabilityList)
            }
        }
    }

    private fun runProgressBar(progressBar: ProgressBar) {
        i = progressBar.progress
        thread {
            kotlin.run {
                while (i < 100) {
                    i++
                    hdlr.post(kotlinx.coroutines.Runnable {
                        pgsBar?.progress = i;
                    })
                    try {
                        Thread.sleep(100)
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }


}