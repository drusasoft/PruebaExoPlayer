package com.exmaple.pruebaexoplayer.activities

import android.os.Build
import android.os.Bundle
import android.view.KeyEvent
import android.view.WindowInsets
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.exmaple.pruebaexoplayer.databinding.LayoutPantallaFullscreenBinding
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem





class ReproductorFullScreen:AppCompatActivity()
{
    private lateinit var binding: LayoutPantallaFullscreenBinding
    private lateinit var exoPlayer:ExoPlayer




    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = LayoutPantallaFullscreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Se oculta la Status Bar
        if(Build.VERSION.SDK_INT >= 30)
            window.insetsController?.hide(WindowInsets.Type.statusBars())

        //Se obtiene la posicion de la reproduccion pasada como parametro desde el FragmentReproductor
        //para continuar a partir de ahi con la reproduccion
        val posicionReproduccion = intent.getLongExtra("PosicionReproduccion", 0)

        configurarReproductor(posicionReproduccion)

    }



    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean
    {

        //Si se pulsa el boton volver del Telefono para salir de la pantalla
        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            //Se guarda en las preferencias de usuario la posisicon actual de la Reproduccion para continuar desde ese punto en el FragmentReproductor
            val prefs = PreferenceManager.getDefaultSharedPreferences(this)
            val edit = prefs.edit()
            edit.putBoolean("VolverPantallaCompleta", true)
            edit.putLong("PosicionReproduccion", exoPlayer.currentPosition)
            edit.commit()
        }

        return super.onKeyDown(keyCode, event)
    }



    override fun onPause()
    {
        super.onPause()

        //Se Pausa la Reproduccion del video
        exoPlayer.pause()
    }



    override fun onDestroy() {
        super.onDestroy()

        //Se libera el Reproductor
        exoPlayer.release()
    }



    private fun configurarReproductor(posicionReproduccion:Long)
    {
        //Se instancia el Reproductor Exoplayer
        exoPlayer = ExoPlayer.Builder(this).build()

        //Se enlaza el Reproducto Exoplayer con el View "StyledPlayerView" definido en el XML
        binding.videoView.player = exoPlayer

        //Se crea el contenido Multimedia a reproducir
        val mediaItem = MediaItem.fromUri("https://firebasestorage.googleapis.com/v0/b/wowi-f5b91.appspot.com/o/videos%2FEurofighter%20y%20F-35%20brit%C3%A1nicos%20y%20Gripen%20checos%20sobre%20los%20cielos%20del%20B%C3%A1ltico%20(720p_25fps_H264-192kbit_AAC).mp4?alt=media&token=a0ca39d8-625b-4ecf-ba46-28abce4c3faa")

        //Se asocia el contenido Multimedia con el Reproductor ExoPlayer y se inicia su reproduccion
        exoPlayer.setMediaItem(mediaItem)
        exoPlayer.prepare()
        exoPlayer.seekTo(posicionReproduccion)//Se continua la reproduccion desde la posicion recibida como parametro
        exoPlayer.play()
    }

}