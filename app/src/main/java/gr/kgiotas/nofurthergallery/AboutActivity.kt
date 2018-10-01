package gr.kgiotas.nofurthergallery

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.github.porokoro.paperboy.builders.buildPaperboy
import de.psdev.licensesdialog.LicensesDialog
import de.psdev.licensesdialog.licenses.ApacheSoftwareLicense20
import de.psdev.licensesdialog.licenses.BSD2ClauseLicense
import de.psdev.licensesdialog.licenses.MITLicense
import de.psdev.licensesdialog.model.Notice
import de.psdev.licensesdialog.model.Notices
import mehdi.sakout.aboutpage.AboutPage
import mehdi.sakout.aboutpage.Element

class AboutActivity : AppCompatActivity(){
    private var aboutPage : View? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val versionElement = Element()
        versionElement.title = resources.getString(R.string.version) + " " + Utils.getAppVersion(this)

        val changelog = Element()
        changelog.title = resources.getString(R.string.changelog)
        changelog.setOnClickListener{_ -> showChangeLog()}

        val opensource = Element()
        opensource.title = resources.getString(R.string.open_source_licenses)
        opensource.setOnClickListener{_ -> showOpenSourceLicences()}

        val sourceCode = Element()
        sourceCode.title = resources.getString(R.string.source_code)
        sourceCode.setOnClickListener{_ -> openLink()}

        aboutPage = AboutPage(this)
                .setImage(R.drawable.launcher_icon_foreground)
                .isRTL(false)
                .setDescription(resources.getString(R.string.description))
                .addGroup(resources.getString(R.string.app_info))
                .addItem(versionElement)
                .addItem(changelog)
                .addItem(opensource)
                .addItem(sourceCode)
                .create()

        setContentView(aboutPage)
    }

    private fun showOpenSourceLicences(){
        val pinchToZoomNotice = Notice("PinchToZoom", "https://github.com/martinwithaar/PinchToZoom", "Copyright 2018 martinwithaar", MITLicense())
        val glideNotice = Notice("Glide", "https://github.com/bumptech/glide", "Copyright 2018 Sam Judd", BSD2ClauseLicense())
        val aboutPageNotice = Notice("android-about-page", "https://github.com/medyo/android-about-page", "Copyright medyo", MITLicense())
        val paperboyNotice = Notice("Paperboy", "https://github.com/porokoro/paperboy", "Copyright (C) 2015-2016 Dominik Hibbeln", ApacheSoftwareLicense20())
        val licensesDiagNotice = Notice("LicensesDialog", "https://github.com/PSDev/LicensesDialog", "Copyright 2013-2017 Philip Schiffer", ApacheSoftwareLicense20())

        val allNotices = Notices()
        allNotices.addNotice(pinchToZoomNotice)
        allNotices.addNotice(glideNotice)
        allNotices.addNotice(aboutPageNotice)
        allNotices.addNotice(paperboyNotice)
        allNotices.addNotice(licensesDiagNotice)

        LicensesDialog.Builder(this).setNotices(allNotices).build().show()
    }

    private fun showChangeLog(){
        setContentView(R.layout.activity_about)
        val ppFrag = buildPaperboy(this){}
        supportFragmentManager.beginTransaction().replace(R.id.about_layout_main, ppFrag, "changelog_frag").addToBackStack(null).commit()
    }

    override fun onBackPressed() {

        val sth = supportFragmentManager.findFragmentByTag("changelog_frag")
        if(sth != null && sth.isVisible){
            setContentView(aboutPage)
        }
        super.onBackPressed()
    }

    private fun openLink(){
        val bintent = Intent( Intent.ACTION_VIEW, Uri.parse(resources.getString(R.string.gitlab_link)))
        startActivity(bintent)
    }
}