import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.tabs.TabLayout
import notq.dccast.DCCastApplication
import notq.dccast.R
import notq.dccast.databinding.VhNavCategoryBinding
import notq.dccast.databinding.VhNavMenu2Binding
import notq.dccast.databinding.VhNavMenuBinding
import notq.dccast.databinding.VhNavProfileBinding
import notq.dccast.screens.home.adapter.AdapterNavigationCategory
import notq.dccast.screens.home.interfaces.HomeChildFragmentListener
import notq.dccast.screens.home.mandu.ManduService
import notq.dccast.util.LoginService
import notq.dccast.util.Util

@Suppress("UNREACHABLE_CODE")
class Demo(private val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var category: ViewHolderCategory
    private var selectedPosition: Int = 0

    fun setSelection(selection: Int) {
        this.selectedPosition = selection

        if (selectedPosition == 0 || selectedPosition == 1 || selectedPosition == 3) {
            notifyItemChanged(1)
        } else if (selectedPosition == 4 || selectedPosition == 5) {
            notifyItemChanged(2)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(context)
        val profileBinding: VhNavProfileBinding
        val menuBinding: VhNavMenuBinding
        val menu2Binding: VhNavMenu2Binding
        val categoryBinding: VhNavCategoryBinding

        return when (getItemViewType(position)) {
            0 -> {
                profileBinding = DataBindingUtil.inflate(inflater, R.layout.vh_nav_profile, viewGroup, false)
                return ViewHolderProfile(profileBinding)
            }

            1 -> {
                menuBinding = DataBindingUtil.inflate(inflater, R.layout.vh_nav_menu, viewGroup, false)
                return ViewHolderMenu(menuBinding)
            }

            2 -> {
                menu2Binding = DataBindingUtil.inflate(inflater, R.layout.vh_nav_menu_2, viewGroup, false)
                return ViewHolderMenu2(menu2Binding)
            }

            3 -> {
                categoryBinding = DataBindingUtil.inflate(inflater, R.layout.vh_nav_category, viewGroup, false)
                category = ViewHolderCategory(categoryBinding)
                return category
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, i: Int) {
        when (getItemViewType(i)) {
            0 -> {
                (viewHolder as ViewHolderProfile).reload()
            }
            1 -> {
                (viewHolder as ViewHolderMenu).reload()
            }

            2 -> {
                (viewHolder as ViewHolderMenu2).reload()
            }

            3 -> {
                (viewHolder as ViewHolderCategory).reload()
            }
        }
    }

    override fun getItemCount(): Int {
        return SECTIONS
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    inner class ViewHolderProfile internal constructor(private val profileBinding: VhNavProfileBinding) : RecyclerView.ViewHolder(profileBinding.root) {

        init {
            profileBinding.lblUsername.setOnClickListener(context as View.OnClickListener)
            profileBinding.ivProfileImage.setOnClickListener(context as View.OnClickListener)
            profileBinding.layoutMandu.setOnClickListener(context as View.OnClickListener)
            profileBinding.btnSettings.setOnClickListener(context as View.OnClickListener)
            profileBinding.btnLoginOrNotification.setOnClickListener(context as View.OnClickListener)
        }

        internal fun reload() {
            val loginUser = LoginService.getLoginUser()
            if (loginUser != null) {
                profileBinding.profileInfo.visibility = View.VISIBLE
                profileBinding.imgLoginOrNotification.setImageResource(R.drawable.ic_notification)
                profileBinding.lblUsername.text = loginUser.getNickName()
                profileBinding.lblUsername.paintFlags = profileBinding.lblUsername.paintFlags or Paint.UNDERLINE_TEXT_FLAG

                val width = context.resources.getDimensionPixelSize(R.dimen.left_menu_profile_image_size)

                Glide.with(context)
                        .load(Util.getValidateUrl(loginUser.getProfileImage()))
                        .apply(
                                RequestOptions()
                                        .override(width, width)
                                        .placeholder(R.drawable.ic_profile_placeholder)
                                        .centerCrop())
                        .into(profileBinding.ivProfileImage)

                if (DCCastApplication.userId != null && DCCastApplication.appId != null) {
                    profileBinding.manduLoading.visibility = View.VISIBLE
                    val service = ManduService(context, object : ManduService.ManduCallback {
                        override fun onError(error: String?) {
                            profileBinding.manduLoading.visibility = View.GONE
                            profileBinding.lblManduCount.text = "0"
                            if (error != null) {
                                Toast.makeText(context, error, Toast.LENGTH_LONG).show()
                            }
                        }

                        override fun onComplete(mandu: Double) {
                            profileBinding.manduLoading.visibility = View.GONE
                            profileBinding.lblManduCount.text = Util.getFormattedNumber(mandu)
                        }
                    })
                    service.getUserMandu()
                } else {
                    profileBinding.manduLoading.visibility = View.GONE
                }
            } else {
                profileBinding.profileInfo.visibility = View.GONE
                profileBinding.imgLoginOrNotification.setImageResource(R.drawable.ic_profile_placeholder)
            }
        }
    }

    inner class ViewHolderMenu internal constructor(private val menuBinding: VhNavMenuBinding) : RecyclerView.ViewHolder(menuBinding.root) {

        init {
            menuBinding.itemHome.setOnClickListener(context as View.OnClickListener)
            menuBinding.itemMyContent.setOnClickListener(context as View.OnClickListener)
            menuBinding.itemFavorite.setOnClickListener(context as View.OnClickListener)
            menuBinding.itemCast.setOnClickListener(context as View.OnClickListener)
        }

        internal fun reload() {
            menuBinding.imgHome.visibility = View.VISIBLE
            menuBinding.imgHomeSelected.visibility = View.GONE
            menuBinding.imgMyContent.visibility = View.VISIBLE
            menuBinding.imgMyContentSelected.visibility = View.GONE
            menuBinding.imgFavorite.visibility = View.VISIBLE
            menuBinding.imgFavoriteSelected.visibility = View.GONE
            menuBinding.imgCast.visibility = View.VISIBLE
            menuBinding.imgCastSelected.visibility = View.GONE

            menuBinding.homeText.setTextColor(Color.parseColor("#383A40"))
            menuBinding.myContentText.setTextColor(Color.parseColor("#383A40"))
            menuBinding.favoriteText.setTextColor(Color.parseColor("#383A40"))
            menuBinding.castListText.setTextColor(Color.parseColor("#383A40"))

            when (selectedPosition) {
                0 -> {
                    menuBinding.imgHome.visibility = View.GONE
                    menuBinding.imgHomeSelected.visibility = View.VISIBLE
                    menuBinding.homeText.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary))
                }

                1 -> {
                    menuBinding.imgMyContent.visibility = View.GONE
                    menuBinding.imgMyContentSelected.visibility = View.VISIBLE
                    menuBinding.myContentText.setTextColor(
                            ContextCompat.getColor(context, R.color.colorPrimary))
                }

                2 -> {
                    menuBinding.imgFavorite.visibility = View.GONE
                    menuBinding.imgFavoriteSelected.visibility = View.VISIBLE
                    menuBinding.favoriteText.setTextColor(
                            ContextCompat.getColor(context, R.color.colorPrimary))
                }

                3 -> {
                    menuBinding.imgCast.visibility = View.GONE
                    menuBinding.imgCastSelected.visibility = View.VISIBLE
                    menuBinding.castListText.setTextColor(
                            ContextCompat.getColor(context, R.color.colorPrimary))
                }
            }
        }
    }

    inner class ViewHolderMenu2 internal constructor(private val menu2Binding: VhNavMenu2Binding) : RecyclerView.ViewHolder(menu2Binding.root) {

        init {

            menu2Binding.itemLive.setOnClickListener(context as View.OnClickListener)
            menu2Binding.itemVod.setOnClickListener(context as View.OnClickListener)
        }

        internal fun reload() {
            menu2Binding.itemLiveContainer.setBackgroundColor(Color.WHITE)
            menu2Binding.itemVodContainer.setBackgroundColor(Color.WHITE)

            when (selectedPosition) {
                4 -> {
                    menu2Binding.itemLiveContainer.setBackgroundColor(
                            ContextCompat.getColor(context, R.color.colorSelected))
                    menu2Binding.itemVodContainer.setBackgroundColor(Color.WHITE)
                }

                5 -> {
                    menu2Binding.itemLiveContainer.setBackgroundColor(Color.WHITE)
                    menu2Binding.itemVodContainer.setBackgroundColor(
                            ContextCompat.getColor(context, R.color.colorSelected))
                }
            }
        }
    }

    inner class ViewHolderCategory internal constructor(private val categoryBinding: VhNavCategoryBinding) : RecyclerView.ViewHolder(categoryBinding.root) {

        init {

            init()
        }

        private fun init() {
            val gridLayoutManager = GridLayoutManager(context, 3)
            gridLayoutManager.orientation = RecyclerView.VERTICAL
            categoryBinding.categoryRv.setHasFixedSize(true)
            categoryBinding.categoryRv.layoutManager = gridLayoutManager
            categoryBinding.categoryRv.adapter = AdapterNavigationCategory(context)
            categoryBinding.tabLayout.getTabAt(1)!!.select()
            categoryBinding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    (context as HomeChildFragmentListener).onLeftMenuTabChanged(tab.position)
                }

                override fun onTabUnselected(tab: TabLayout.Tab) {

                }

                override fun onTabReselected(tab: TabLayout.Tab) {

                }
            })
        }

        fun selectTab(position: Int) {
            if (position != categoryBinding.tabLayout.selectedTabPosition) {
                categoryBinding.tabLayout.getTabAt(position)!!.select()
                notifyItemChanged(position)
            }
        }

        internal fun reload() {

        }
    }

    companion object {
        private val SECTIONS = 4 //1:profile, 2:menus, 3:menus #2, 4:categories
    }
}