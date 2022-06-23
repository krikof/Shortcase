package com.example.shortcaserc.ui.home

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.view.isVisible
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import androidx.savedstate.findViewTreeSavedStateRegistryOwner
import coil.load
import com.example.shortcaserc.R
import com.example.shortcaserc.database.ComicObject
import com.example.shortcaserc.utils.Utils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import androidx.core.content.ContextCompat.startActivity

import android.content.ActivityNotFoundException

import android.content.ComponentName
import android.net.Uri
import androidx.core.content.ContextCompat


class ComicCardView : ConstraintLayout {
    constructor(context: Context) : super(context) {
        initialize(context)
    }

    constructor(context: Context, attributeSet: AttributeSet?) : super(context, attributeSet) {
        initialize(context)
    }

    constructor(
        context: Context,
        attributeSet: AttributeSet?,
        defStyleAttr: Int
    ) : super(
        context,
        attributeSet,
        defStyleAttr
    ) {
        initialize(context)
    }

    private var scope: CoroutineScope? = null

    private val homeViewModel by lazy {
        val factory = HomeViewModel.Factory(findViewTreeSavedStateRegistryOwner()!!)
        ViewModelProvider(findViewTreeViewModelStoreOwner()!!, factory)[HomeViewModel::class.java]
    }

    private val comicCardView: CardView by lazy { findViewById(R.id.comicCardView) }

    private val comicImageView: ImageView by lazy { findViewById(R.id.comicImageView) }

    private val comicTitleTextView: TextView by lazy { findViewById(R.id.comicTitleTextView) }
    private val comicPublishedTextView: TextView by lazy { findViewById(R.id.comicPublishedTextView) }
    private val comicIssueTextView: TextView by lazy { findViewById(R.id.comicIssueTextView) }

    private val explanationButton: Button by lazy { findViewById(R.id.explainationButton) }
    private val favoriteButton: Button by lazy { findViewById(R.id.favoriteButton) }
    private val shareButton: Button by lazy { findViewById(R.id.shareButton) }

    private val loader: ProgressBar by lazy { findViewById(R.id.comicProgressBar) }
    private val errorTextView: TextView by lazy { findViewById(R.id.errorTextView) }


    private fun initialize(context: Context) {
        inflate(context, R.layout.comic_card_view, this)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

        initComic()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        scope?.cancel()
        scope = null
    }

    private fun initComic() {
        homeViewModel.checkForInternetConnectivity { response ->
            comicCardView.isVisible = false
            errorTextView.isVisible = false
            loader.isVisible = true

            if (response) {
                try {
                    homeViewModel.initFirstComic()
                    homeViewModel.currentComicInView.observe(context as LifecycleOwner) {
                        setComicDetails(it)
                        setOnClickListeners()
                    }
                } catch (e: Error) {
                    Log.e("initComic", "Something went wrong while init comic")
                    errorTextView.isVisible = true
                    loader.isVisible = false
                }
            } else {
                errorTextView.isVisible = true
                loader.isVisible = false
            }
        }
    }

    private fun setComicDetails(comic: ComicObject) {
        comicCardView.isVisible = true
        errorTextView.isVisible = false
        loader.isVisible = false

        val utils = Utils()
        val date = try {
            utils.dateFormatter(comic.day.toInt(), comic.month.toInt(), comic.year.toInt())
        } catch (e: Error) {
            Log.e("Date Formatting", "Something went wrong while formatting")
        }

        comicImageView.load(comic.img)
        comicTitleTextView.text = "Title: ${comic.title}"
        comicPublishedTextView.text = "Published: ${date}"
        comicIssueTextView.text = "Comic issue: ${comic.num}"
    }


    private fun setOnClickListeners() {
        explanationButton.setOnClickListener {
            Toast.makeText(context, "Explaination clicked", Toast.LENGTH_SHORT).show()

            // https://www.explainxkcd.com/wiki/index.php/2632:_Greatest_Scientist

            /*homeViewModel.explainComic {
                // TODO - Pull out of view
                // TODO - doesn't work as expected, weird crashes

                val baseUrl = "https://www.explainxkcd.com/wiki/index.php/"
                var comicNum = 2632
                var comicTitle: String = "Greatest Scientist".replace(" ", "_")

                val newUrl = "$baseUrl/${comicNum}_$comicTitle"

                try {
                    val i = Intent("android.intent.action.MAIN")
                    i.component =
                        ComponentName.unflattenFromString("com.android.chrome/com.android.chrome.Main")
                    i.addCategory("android.intent.category.LAUNCHER")
                    i.data = Uri.parse(baseUrl)
                    context.startActivity(i)
                } catch (e: ActivityNotFoundException) {
                    // Chrome is not installed
                    val i = Intent(Intent.ACTION_VIEW, Uri.parse(baseUrl))
                    context.startActivity(i)
                }
            }*/
        }

        favoriteButton.setOnClickListener {
            Toast.makeText(context, "Added to favorites", Toast.LENGTH_SHORT).show()
            homeViewModel.favoriteComic()
        }

        shareButton.setOnClickListener {
            // TODO - Pull out of view
            // TODO - doesn't work as expected, object is null

            Toast.makeText(context, "Share clicked", Toast.LENGTH_SHORT).show()

            homeViewModel.shareComic { comic ->
                val intent: Intent = Intent(Intent.ACTION_SEND)
                intent.type = "text/plain"

                intent.putExtra(Intent.EXTRA_TEXT, comic.link)
                context.startActivity(Intent.createChooser(intent, "Share using.."))
            }
        }
        shareButton.setOnLongClickListener {
            // TODO - Pull out of view
            // TODO - doesn't work as expected, object is null
            Toast.makeText(context, "Check notifications..", Toast.LENGTH_SHORT).show()

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                homeViewModel.simulateNotification {
                    val notification = NotificationCompat.Builder(context, "channelID")
                        .setContentTitle("New comic posted")
                        .setSmallIcon(R.drawable.ic_baseline_info_24)
                        .setContentText("TODO: intent to relevant comic")
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .build()

                    val notificationManager = NotificationManagerCompat.from(context)

                    val channel = NotificationChannel(
                        "channelID",
                        "channelName",
                        NotificationManager.IMPORTANCE_DEFAULT
                    )

                    val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                    manager.createNotificationChannel(channel)

                    notificationManager.notify(0, notification)
                }
            }
            true
        }
    }


}