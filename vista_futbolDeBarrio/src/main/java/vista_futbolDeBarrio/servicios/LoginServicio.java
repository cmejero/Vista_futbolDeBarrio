package vista_futbolDeBarrio.servicios;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

import vista_futbolDeBarrio.dtos.ClubDto;
import vista_futbolDeBarrio.dtos.InstalacionDto;
import vista_futbolDeBarrio.dtos.RespuestaLoginDto;
import vista_futbolDeBarrio.dtos.UsuarioDto;

public class LoginServicio {

    public RespuestaLoginDto login(String email, String password) {
        try {
            JSONObject json = new JSONObject();
            json.put("email", email);
            json.put("password", password);

            System.out.println("JSON de solicitud: " + json.toString());

            String urlApi = "http://localhost:9527/api/login";
            URL url = new URL(urlApi);
            HttpURLConnection conex = (HttpURLConnection) url.openConnection();
            conex.setRequestMethod("POST");
            conex.setRequestProperty("Content-Type", "application/json");
            conex.setDoOutput(true);

            try (OutputStream os = conex.getOutputStream()) {
                byte[] input = json.toString().getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int codigoRespuesta = conex.getResponseCode();
            System.out.println("Código de respuesta: " + codigoRespuesta);

            if (codigoRespuesta == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conex.getInputStream()));
                StringBuilder respuesta = new StringBuilder();
                String linea;
                while ((linea = in.readLine()) != null) {
                    respuesta.append(linea);
                }
                in.close();

                System.out.println("Respuesta del servidor: " + respuesta.toString());

                try {
                    JSONObject jsonRespuesta = new JSONObject(respuesta.toString());

                    RespuestaLoginDto respuestaLogin = new RespuestaLoginDto();
                    respuestaLogin.setToken(jsonRespuesta.getString("token"));
                    respuestaLogin.setTipoUsuario(jsonRespuesta.getString("tipoUsuario"));

                    String tipoUsuario = respuestaLogin.getTipoUsuario();
                    JSONObject datos = jsonRespuesta.getJSONObject("datosUsuario");

                    if ("administrador".equals(tipoUsuario) || "jugador".equals(tipoUsuario)) {
                        UsuarioDto usuarioDto = new UsuarioDto();
                        usuarioDto.setIdUsuario(datos.getLong("idUsuario"));
                        usuarioDto.setNombreCompletoUsuario(datos.getString("nombreCompletoUsuario"));
                        usuarioDto.setEmailUsuario(datos.getString("emailUsuario"));
                        respuestaLogin.setDatosUsuario(usuarioDto);
                    } else if ("club".equals(tipoUsuario)) {
                        ClubDto clubDto = new ClubDto();
                        clubDto.setIdClub(datos.getLong("idClub"));
                        clubDto.setNombreClub(datos.getString("nombreClub"));
                        respuestaLogin.setDatosUsuario(clubDto);
                    } else if ("instalacion".equals(tipoUsuario)) {
                        InstalacionDto instalacionDto = new InstalacionDto();
                        instalacionDto.setIdInstalacion(datos.getLong("idInstalacion"));
                        instalacionDto.setNombreInstalacion(datos.getString("nombreInstalacion"));
                        respuestaLogin.setDatosUsuario(instalacionDto);
                    }

                    return respuestaLogin;

                } catch (Exception e) {
                    System.out.println("La respuesta no es un JSON válido. Respuesta: " + respuesta.toString());
                    e.printStackTrace();
                }
            } else {
                System.out.println("Error al hacer login: " + codigoRespuesta);
            }

        } catch (Exception e) {
            System.out.println("ERROR - [ServiciosLogin] " + e);
            e.printStackTrace();
        }
        return null;
    }
}
