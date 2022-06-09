package com.exmaple.pruebaexoplayer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.preference.PreferenceManager
import com.exmaple.pruebaexoplayer.R
import com.exmaple.pruebaexoplayer.databinding.LayoutFragmentReproductorBinding
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player





class FragmentReproductor:Fragment()
{

    private lateinit var binding: LayoutFragmentReproductorBinding
    private lateinit var navController:NavController
    private lateinit var exoPlayer:ExoPlayer




    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        //Se instancia el Reproductor Exoplayer
        exoPlayer = ExoPlayer.Builder(requireContext()).build()

        //se registra el listener para recibir eventos del Reproductor
        exoPlayer.addListener(object: Player.Listener{

            //se ejecuta cuando cambia el estado de Reproduccion del Reproductor
            override fun onIsPlayingChanged(isPlaying: Boolean) {
                super.onIsPlayingChanged(isPlaying)

                //Si Se esta reproduciendo el video
                if(isPlaying)
                    binding.textViewFullScreen.visibility = View.VISIBLE //Se muestra la opcion de Reproducio en Pantalla Completa
            }

        })



    }



    override fun onStart()
    {
        super.onStart()

        //Se comprueba si hay valores guardados en las Preferencias de susuario, lo que significa que se ha Vuelto de la Actividad ReproductorFullScreen
        getPreferenciasUsuario()
    }



    override fun onPause() {
        super.onPause()

        //Se pausa la reproduccion
        exoPlayer.pause()
    }



    override fun onDestroy() {
        super.onDestroy()

        //Se libera el Reproductor
        exoPlayer.release()
    }



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
    {
        binding = LayoutFragmentReproductorBinding.inflate(inflater, container, false)


        //Se enlaza el Reproducto Exoplayer con el View "StyledPlayerView" definido en el XML
        binding.videoView.player = exoPlayer

        //Se crea el contenido Multimedia a reproducir
        val mediaItem = MediaItem.fromUri("https://firebasestorage.googleapis.com/v0/b/wowi-f5b91.appspot.com/o/videos%2FEurofighter%20y%20F-35%20brit%C3%A1nicos%20y%20Gripen%20checos%20sobre%20los%20cielos%20del%20B%C3%A1ltico%20(720p_25fps_H264-192kbit_AAC).mp4?alt=media&token=a0ca39d8-625b-4ecf-ba46-28abce4c3faa")

        //Se asocia el contenido Multimedia con el Reproductor ExoPlayer y se inicia su reproduccion
        exoPlayer.setMediaItem(mediaItem)
        exoPlayer.prepare()
        exoPlayer.play()


        //****************ClickListener****************

        binding.textViewFullScreen.setOnClickListener {

            //se va a la Activity que muestra la reproduccion en pantalla completa
            //y se pasa el tiempo de reproduccion transcurrido para asi continuar la reproduccion desde ese punto
            val bundle = Bundle()
            bundle.putLong("PosicionReproduccion", exoPlayer.currentPosition)
            navController.navigate(R.id.irReproductorFullScreen, bundle)
        }

        //****************Fin ClickListener****************

        return binding.root

    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Se instancia el objeto NavController
        navController = Navigation.findNavController(view)
    }



    //se comprueba si hay almacenados valores en las Preferencias de usaurio, lo cual significa que se ha regresado de la Actividad
    //que reproduce el video en pantalla completa
    private fun getPreferenciasUsuario()
    {
        val prefs = PreferenceManager.getDefaultSharedPreferences(requireContext())
        val volverPantallaCompleta = prefs.getBoolean("VolverPantallaCompleta", false)

        if(volverPantallaCompleta)
        {
            //Como se ha regresado de la Actividad a que reproduce Pantalla Completa, se obtiene la posicion de reproduccion guardada en las Preferencias de Usuario
            //Y se continua con la Reproduccion desde ese puto
            val posicionReproduccion = prefs.getLong("PosicionReproduccion", 0)
            exoPlayer.seekTo(posicionReproduccion)
            exoPlayer.play()

            //se pone el valor a false en las preferencias de Usuario
            val edit = prefs.edit()
            edit.putBoolean("VolverPantallaCompleta", false)
            edit.commit()
        }

    }

}