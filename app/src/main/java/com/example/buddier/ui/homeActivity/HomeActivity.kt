package com.example.buddier.ui.homeActivity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.view.animation.LinearInterpolator
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DiffUtil
import com.example.buddier.R
import com.example.buddier.databinding.ActivityHomeBinding
import com.example.buddier.ui.homeActivity.model.PetModel
import com.example.buddier.ui.signupActivity.SignupViewModel
import com.google.android.material.navigation.NavigationView
import com.yuyakaido.android.cardstackview.*
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity: AppCompatActivity(), CardStackListener {
    private lateinit var binding: ActivityHomeBinding

    private val drawerLayout by lazy { findViewById<DrawerLayout>(R.id.drawer_layout) }
    private val cardStackView by lazy { findViewById<CardStackView>(R.id.card_stack_view) }
    private val manager by lazy { CardStackLayoutManager(this, this) }
    private val adapter by lazy { CardStackAdapter(createSpots()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupNavigation()
        setupCardStackView()
        setupButton()
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawers()
        } else {
            super.onBackPressed()
        }
    }

    override fun onCardDragging(direction: Direction, ratio: Float) {
        Log.d("CardStackView", "onCardDragging: d = ${direction.name}, r = $ratio")
    }

    override fun onCardSwiped(direction: Direction) {
        Log.d("CardStackView", "onCardSwiped: p = ${manager.topPosition}, d = $direction")
        if (manager.topPosition == adapter.itemCount - 5) {
            paginate()
        }
    }

    override fun onCardRewound() {
        Log.d("CardStackView", "onCardRewound: ${manager.topPosition}")
    }

    override fun onCardCanceled() {
        Log.d("CardStackView", "onCardCanceled: ${manager.topPosition}")
    }

    override fun onCardAppeared(view: View, position: Int) {
        val textView = view.findViewById<TextView>(R.id.item_name)
        Log.d("CardStackView", "onCardAppeared: ($position) ${textView.text}")
    }

    override fun onCardDisappeared(view: View, position: Int) {
        val textView = view.findViewById<TextView>(R.id.item_name)
        Log.d("CardStackView", "onCardDisappeared: ($position) ${textView.text}")
    }

    private fun setupNavigation() {
        // NavigationView
        val navigationView = findViewById<NavigationView>(R.id.navigation_view)
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.reload -> reload()
            }
            drawerLayout.closeDrawers()
            true
        }
    }

    private fun setupCardStackView() {
        initialize()
    }

    private fun setupButton() {
        val skip = findViewById<View>(R.id.skip_button)
        skip.setOnClickListener {
            val setting = SwipeAnimationSetting.Builder()
                .setDirection(Direction.Left)
                .setDuration(Duration.Normal.duration)
                .setInterpolator(AccelerateInterpolator())
                .build()
            manager.setSwipeAnimationSetting(setting)
            cardStackView.swipe()
        }

        val rewind = findViewById<View>(R.id.rewind_button)
        rewind.setOnClickListener {
            val setting = RewindAnimationSetting.Builder()
                .setDirection(Direction.Bottom)
                .setDuration(Duration.Normal.duration)
                .setInterpolator(DecelerateInterpolator())
                .build()
            manager.setRewindAnimationSetting(setting)
            cardStackView.rewind()
        }

        val like = findViewById<View>(R.id.like_button)
        like.setOnClickListener {
            val setting = SwipeAnimationSetting.Builder()
                .setDirection(Direction.Right)
                .setDuration(Duration.Normal.duration)
                .setInterpolator(AccelerateInterpolator())
                .build()
            manager.setSwipeAnimationSetting(setting)
            cardStackView.swipe()
        }
    }

    private fun initialize() {
        manager.setStackFrom(StackFrom.None)
        manager.setVisibleCount(3)
        manager.setTranslationInterval(8.0f)
        manager.setScaleInterval(0.95f)
        manager.setSwipeThreshold(0.3f)
        manager.setMaxDegree(20.0f)
        manager.setDirections(Direction.HORIZONTAL)
        manager.setCanScrollHorizontal(true)
        manager.setCanScrollVertical(true)
        manager.setSwipeableMethod(SwipeableMethod.AutomaticAndManual)
        manager.setOverlayInterpolator(LinearInterpolator())
        cardStackView.layoutManager = manager
        cardStackView.adapter = adapter
        cardStackView.itemAnimator.apply {
            if (this is DefaultItemAnimator) {
                supportsChangeAnimations = false
            }
        }
    }

    private fun paginate() {
        val old = adapter.getSpots()
        val new = old.plus(createSpots())
        val callback = SpotDiffCallback(old, new)
        val result = DiffUtil.calculateDiff(callback)
        adapter.setSpots(new)
        result.dispatchUpdatesTo(adapter)
    }

    private fun reload() {
        val old = adapter.getSpots()
        val new = createSpots()
        val callback = SpotDiffCallback(old, new)
        val result = DiffUtil.calculateDiff(callback)
        adapter.setSpots(new)
        result.dispatchUpdatesTo(adapter)
    }

    private fun createSpots(): List<PetModel> {
        val petModels = ArrayList<PetModel>()
        petModels.add(PetModel(name = "Sasha", age = 10, description = "groenendael", url = "https://images.dog.ceo/breeds/groenendael/n02105056_6338.jpg"))
        petModels.add(PetModel(name = "Mark", age = 5,description = "mastiff-bull", url = "https://images.dog.ceo/breeds/mastiff-bull/n02108422_1546.jpg"))
        petModels.add(PetModel(name = "Forest", age = 3,description = "redbone", url = "https://images.dog.ceo/breeds/redbone/n02090379_5326.jpg"))
        petModels.add(PetModel(name = "Brooklyn", age = 4,description = "terrier-bedlington", url = "https://images.dog.ceo/breeds/terrier-bedlington/n02093647_356.jpg"))
        petModels.add(PetModel(name = "Empire", age = 3,description = "schnauzer-miniature", url = "https://images.dog.ceo/breeds/schnauzer-miniature/n02097047_2910.jpg"))
        petModels.add(PetModel(name = "Liberty", age = 7,description = "hound-blood", url = "https://images.dog.ceo/breeds/hound-blood/n02088466_6775.jpg"))
        petModels.add(PetModel(name = "Louvre", age = 2,description = "entlebucher", url = "https://images.dog.ceo/breeds/entlebucher/n02108000_364.jpg"))
        petModels.add(PetModel(name = "Eiffel", age = 6,description = "dhole", url = "https://images.dog.ceo/breeds/dhole/n02115913_1493.jpg"))
        petModels.add(PetModel(name = "Ben", age = 8,description = "redbone", url = "https://images.dog.ceo/breeds/redbone/n02090379_3445.jpg"))
        petModels.add(PetModel(name = "China", age = 1,description = "setter-gordon", url = "https://images.dog.ceo/breeds/setter-gordon/n02101006_1804.jpg"))
        return petModels
    }
}