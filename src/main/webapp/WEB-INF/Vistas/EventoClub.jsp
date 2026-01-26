<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
// Obtener tipo de usuario de sesión y normalizar a minúsculas
String tipoUsuario = ((String) session.getAttribute("tipoUsuario"));

// Redirigir si no es club
if (tipoUsuario == null || !"club".equals(tipoUsuario)) {
	response.sendRedirect("acceso_denegado.jsp");
	return; // Detener el renderizado de la página
}

Long clubId = (Long) session.getAttribute("clubId");
String nombreUsuario = (String) session.getAttribute("nombreClub");
if (nombreUsuario == null)
	nombreUsuario = "Invitado";

Boolean esPremium = (Boolean) session.getAttribute("esPremium");
if (esPremium == null)
	esPremium = false;
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!-- Estilos CSS -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/Css/Estilo.css">

<!-- Bootstrap CSS -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet">

<link
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css"
	rel="stylesheet">

<!-- DataTables CSS -->
<link rel="stylesheet"
	href="https://cdn.datatables.net/1.13.6/css/jquery.dataTables.min.css">

<!-- jQuery (Debe estar antes de Bootstrap y DataTables) -->
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

<!-- Bootstrap JS -->
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

<!-- DataTables JS -->
<script
	src="https://cdn.datatables.net/1.13.6/js/jquery.dataTables.min.js"></script>

<!-- Chart.js (Solo si lo necesitas) -->
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

<title>FUTBOL DE BARRIO</title>
</head>
<body>
	<header class="fixed-top">
		<!-- Contenedor principal de -->
		<div class="container-fluid ">
			<div class="row ">

				<div class="col-sm-12 col-md-12  d-sm-block d-md-block d-none">
					<div class="row">

						<!-- columna logo -->
						<div class="col-sm-1 col-md-1 logo"
							style="background-color: white; border-top: 2px solid black; border-left: 1px solid black">
							<img src="${pageContext.request.contextPath}/Imagenes/LOGOWEB.PNG"></img>
						</div>

						<!-- Columna derecha que se divide en 2 filas -->
						<div class="col-sm-11 col-md-11">
							<div class="row ">
								<!-- fila arriba -->
								<div class="col-sm-12 col-md-12" style="border: solid 2px black">
									<div class="row">
										<div class="col-sm-3 col-md-3 cabeceraArriba p-2 "></div>
										<div class="col-sm-3 col-md-3 cabeceraArriba "
											style="justify-content: right;">
											<svg xmlns="http://www.w3.org/2000/svg" width="1.3vw"
												height="1.3vw" fill="currentColor"
												style="margin-right: 4px; color: white;"
												class="bi bi-envelope-at-fill" viewBox="0 0 16 16">
  <path
													d="M2 2A2 2 0 0 0 .05 3.555L8 8.414l7.95-4.859A2 2 0 0 0 14 2zm-2 9.8V4.698l5.803 3.546zm6.761-2.97-6.57 4.026A2 2 0 0 0 2 14h6.256A4.5 4.5 0 0 1 8 12.5a4.49 4.49 0 0 1 1.606-3.446l-.367-.225L8 9.586zM16 9.671V4.697l-5.803 3.546.338.208A4.5 4.5 0 0 1 12.5 8c1.414 0 2.675.652 3.5 1.671" />
  <path
													d="M15.834 12.244c0 1.168-.577 2.025-1.587 2.025-.503 0-1.002-.228-1.12-.648h-.043c-.118.416-.543.643-1.015.643-.77 0-1.259-.542-1.259-1.434v-.529c0-.844.481-1.4 1.26-1.4.585 0 .87.333.953.63h.03v-.568h.905v2.19c0 .272.18.42.411.42.315 0 .639-.415.639-1.39v-.118c0-1.277-.95-2.326-2.484-2.326h-.04c-1.582 0-2.64 1.067-2.64 2.724v.157c0 1.867 1.237 2.654 2.57 2.654h.045c.507 0 .935-.07 1.18-.18v.731c-.219.1-.643.175-1.237.175h-.044C10.438 16 9 14.82 9 12.646v-.214C9 10.36 10.421 9 12.485 9h.035c2.12 0 3.314 1.43 3.314 3.034zm-4.04.21v.227c0 .586.227.8.581.8.31 0 .564-.17.564-.743v-.367c0-.516-.275-.708-.572-.708-.346 0-.573.245-.573.791" />
</svg>
											futboldebarriosevilla@gmail.com
										</div>
										<div class="col-sm-2 col-md-2 cabeceraArriba">
											<svg xmlns="http://www.w3.org/2000/svg" width="1.2vw"
												height="1.2vw" fill="currentColor"
												style="margin-right: 4px; color: white;"
												class="bi bi-telephone-fill" viewBox="0 0 16 16">
  <path fill-rule="evenodd"
													d="M1.885.511a1.745 1.745 0 0 1 2.61.163L6.29 2.98c.329.423.445.974.315 1.494l-.547 2.19a.68.68 0 0 0 .178.643l2.457 2.457a.68.68 0 0 0 .644.178l2.189-.547a1.75 1.75 0 0 1 1.494.315l2.306 1.794c.829.645.905 1.87.163 2.611l-1.034 1.034c-.74.74-1.846 1.065-2.877.702a18.6 18.6 0 0 1-7.01-4.42 18.6 18.6 0 0 1-4.42-7.009c-.362-1.03-.037-2.137.703-2.877z" />
</svg>
											653435344
										</div>
										<div class="col-sm-2 col-md-2 cabeceraArriba "
											style="justify-content: left;">
											<a href="">
												<button type="button" class="botonCabeceraContactar"
													onclick="abrirGmail()">CONTACTAR</button>
											</a>
										</div>
										<div class="col-sm-2 col-md-2 cabeceraArriba">
											<a href=""> <svg xmlns="http://www.w3.org/2000/svg"
													width="1.4vw" height="1.2vw" fill="currentColor"
													style="margin-right: 3px; color: white;" bi
													bi-instagram" viewBox="0 0 16 16">
  <path
														d="M8 0C5.829 0 5.556.01 4.703.048 3.85.088 3.269.222 2.76.42a3.9 3.9 0 0 0-1.417.923A3.9 3.9 0 0 0 .42 2.76C.222 3.268.087 3.85.048 4.7.01 5.555 0 5.827 0 8.001c0 2.172.01 2.444.048 3.297.04.852.174 1.433.372 1.942.205.526.478.972.923 1.417.444.445.89.719 1.416.923.51.198 1.09.333 1.942.372C5.555 15.99 5.827 16 8 16s2.444-.01 3.298-.048c.851-.04 1.434-.174 1.943-.372a3.9 3.9 0 0 0 1.416-.923c.445-.445.718-.891.923-1.417.197-.509.332-1.09.372-1.942C15.99 10.445 16 10.173 16 8s-.01-2.445-.048-3.299c-.04-.851-.175-1.433-.372-1.941a3.9 3.9 0 0 0-.923-1.417A3.9 3.9 0 0 0 13.24.42c-.51-.198-1.092-.333-1.943-.372C10.443.01 10.172 0 7.998 0zm-.717 1.442h.718c2.136 0 2.389.007 3.232.046.78.035 1.204.166 1.486.275.373.145.64.319.92.599s.453.546.598.92c.11.281.24.705.275 1.485.039.843.047 1.096.047 3.231s-.008 2.389-.047 3.232c-.035.78-.166 1.203-.275 1.485a2.5 2.5 0 0 1-.599.919c-.28.28-.546.453-.92.598-.28.11-.704.24-1.485.276-.843.038-1.096.047-3.232.047s-2.39-.009-3.233-.047c-.78-.036-1.203-.166-1.485-.276a2.5 2.5 0 0 1-.92-.598 2.5 2.5 0 0 1-.6-.92c-.109-.281-.24-.705-.275-1.485-.038-.843-.046-1.096-.046-3.233s.008-2.388.046-3.231c.036-.78.166-1.204.276-1.486.145-.373.319-.64.599-.92s.546-.453.92-.598c.282-.11.705-.24 1.485-.276.738-.034 1.024-.044 2.515-.045zm4.988 1.328a.96.96 0 1 0 0 1.92.96.96 0 0 0 0-1.92m-4.27 1.122a4.109 4.109 0 1 0 0 8.217 4.109 4.109 0 0 0 0-8.217m0 1.441a2.667 2.667 0 1 1 0 5.334 2.667 2.667 0 0 1 0-5.334" />
</svg>
											</a> <a href=""> <svg xmlns="http://www.w3.org/2000/svg"
													width="1.4vw" height="1.2vw" fill="currentColor"
													style="margin-right: 3px; color: white;"
													class="bi bi-twitter-x" viewBox="0 0 16 16">
  <path
														d="M12.6.75h2.454l-5.36 6.142L16 15.25h-4.937l-3.867-5.07-4.425 5.07H.316l5.733-6.57L0 .75h5.063l3.495 4.633L12.601.75Zm-.86 13.028h1.36L4.323 2.145H2.865z" />
</svg>
											</a> <a href=""> <svg xmlns="http://www.w3.org/2000/svg"
													width="1.4vw" height="1.4vw" fill="currentColor"
													style="margin-right: 3px; color: white;"
													class="bi bi-youtube" viewBox="0 0 16 16">
  <path
														d="M8.051 1.999h.089c.822.003 4.987.033 6.11.335a2.01 2.01 0 0 1 1.415 1.42c.101.38.172.883.22 1.402l.01.104.022.26.008.104c.065.914.073 1.77.074 1.957v.075c-.001.194-.01 1.108-.082 2.06l-.008.105-.009.104c-.05.572-.124 1.14-.235 1.558a2.01 2.01 0 0 1-1.415 1.42c-1.16.312-5.569.334-6.18.335h-.142c-.309 0-1.587-.006-2.927-.052l-.17-.006-.087-.004-.171-.007-.171-.007c-1.11-.049-2.167-.128-2.654-.26a2.01 2.01 0 0 1-1.415-1.419c-.111-.417-.185-.986-.235-1.558L.09 9.82l-.008-.104A31 31 0 0 1 0 7.68v-.123c.002-.215.01-.958.064-1.778l.007-.103.003-.052.008-.104.022-.26.01-.104c.048-.519.119-1.023.22-1.402a2.01 2.01 0 0 1 1.415-1.42c.487-.13 1.544-.21 2.654-.26l.17-.007.172-.006.086-.003.171-.007A100 100 0 0 1 7.858 2zM6.4 5.209v4.818l4.157-2.408z" />
</svg>
											</a> <a href=""> <svg xmlns="http://www.w3.org/2000/svg"
													width="1.4vw" height="1.2vw" fill="currentColor"
													style="margin-right: 3px; color: white;"
													class="bi bi-facebook" viewBox="0 0 16 16">
  <path
														d="M16 8.049c0-4.446-3.582-8.05-8-8.05C3.58 0-.002 3.603-.002 8.05c0 4.017 2.926 7.347 6.75 7.951v-5.625h-2.03V8.05H6.75V6.275c0-2.017 1.195-3.131 3.022-3.131.876 0 1.791.157 1.791.157v1.98h-1.009c-.993 0-1.303.621-1.303 1.258v1.51h2.218l-.354 2.326H9.25V16c3.824-.604 6.75-3.934 6.75-7.951" />
</svg>
											</a> <a href=""> <svg xmlns="http://www.w3.org/2000/svg"
													width="1.4vw" height="1.2vw" fill="currentColor"
													style="margin-right: 3px; color: white;"
													class="bi bi-tiktok" viewBox="0 0 16 16">
  <path
														d="M9 0h1.98c.144.715.54 1.617 1.235 2.512C12.895 3.389 13.797 4 15 4v2c-1.753 0-3.07-.814-4-1.829V11a5 5 0 1 1-5-5v2a3 3 0 1 0 3 3z" />
</svg>
											</a>
										</div>

									</div>
								</div>
								<!-- fila medio -->
								<div class="col-sm-12 col-md-12 "
									style="border-left: 2px solid black; border-right: 2px solid black;">
									<div class="row">
										<div class="col-sm-6 col-md-6 cabeceraMedioTitulo">
											<span style="color: #d4af37; margin-right: 7px;"> / </span>
											FUTBOL DE BARRIO <span
												style="color: #c0c0c0; margin-left: 7px;"> /</span>
										</div>
										<div class="col-sm-3 col-md-3 cabeceraMedio">
											<%
											if (esPremium != null && esPremium) {
											%>
											<div class="premium-badge">
												<svg xmlns="http://www.w3.org/2000/svg" fill="currentColor"
													class="bi bi-star-fill" viewBox="0 0 16 16">
            <path
														d="M3.612 15.443c-.386.198-.824-.149-.746-.592l.83-4.73L.173 6.765c-.329-.32-.158-.888.283-.95l4.898-.696 2.194-4.445c.197-.398.73-.398.927 0l2.194 4.445 4.898.696c.441.062.612.63.283.95l-3.523 3.356.83 4.73c.078.443-.36.79-.746.592L8 13.187l-4.389 2.256z" />
          </svg>
												PREMIUM
											</div>
											<%
											} else {
											%>
											<div class=" cabeceraMedio">
												<a href="PagoPremium.jsp">
													<button type="button" class=" botonPremiumCabecera"
														title="Accede a todas las funciones premium y ventajas exclusivas">
														HAZTE PREMIUM</button>
												</a>
											</div>
											<%
											}
											%>


										</div>
										<div class="col-sm-3 col-md-3 cabeceraMedio"
											style="text-decoration: underline;">
											<a href="" class="letraCabeceraMedio"
												id="nombreUsuarioCabecera"> BIENVENIDO: <%=nombreUsuario%>
											</a>
										</div>

									</div>
								</div>
							</div>
						</div>
						<!-- fila de abajo -->
						<div class="col-sm-12 col-md-12  "
							style="border: solid 2px black; background-color: #004000; box-shadow: 0px 4px 8px -4px rgba(0, 0, 0, 0.45);">
							<div class="row " style="background-color: #004000;">
								<!-- columna iquierda -->
								<div class="col-sm-5 col-md-5 ">
									<div class="row  ">
										<div class="col-sm-1 col-md-1 cabeceraAbajo  "></div>
										<div class="col-sm-3 col-md-3 cabeceraAbajo ">
											<a href="Club.jsp" class="letraCabeceraAbajo">INICIO</a>
										</div>
										<div class="col-sm-4 col-md-4 cabeceraAbajo ">
											<a href="" class="letraCabeceraAbajo seccion-bloqueada">ALQUILERES
												<span class="tooltip-text">Sección en desarrollo</span>
											</a>
										</div>
										<div class="col-sm-3 col-md-3 cabeceraAbajo ">
											<a href="EventoClub.jsp" class="letraCabeceraAbajo"
												style="color: #d4af37;">EVENTOS</a>
										</div>
										<div class="col-sm-1 col-md-1 cabeceraAbajo"></div>

									</div>
								</div>

								<!-- columna derecha -->
								<div class="col-sm-7 col-md-7 ">

									<div class="row">
										<div class="col-sm-1 col-md-1 cabeceraAbajo "></div>
										<div class="col-sm-3 col-md-3 cabeceraAbajo ">
											<a href="PlantillaClub.jsp" class="letraCabeceraAbajo">PLANTILLA</a>
										</div>
										<div class="col-sm-3 col-md-3 cabeceraAbajo ">
											<a href="MarcadoresClub.jsp" class="letraCabeceraAbajo">MARCADORES</a>
										</div>
										<div class="col-sm-3 col-md-3 cabeceraAbajo ">
											<a href="" class="letraCabeceraAbajo seccion-bloqueada">DESAFIOS
												<span class="tooltip-text">Sección en desarrollo</span>
											</a>
										</div>
										<div class="col-sm-2 col-md-2 cabeceraAbajo">
											<div class="dropdown">
												<button class="btn btn-secondary  " type="button"
													data-bs-toggle="dropdown" aria-expanded="false"
													style="background-color: #004000; width: 4vw; height: 2.3vw; border-radius: 5px; display: flex; justify-content: center; align-items: center; padding: 0;">
													<svg xmlns="http://www.w3.org/2000/svg" fill="currentColor"
														class="bi bi-list letraCabeceraAbajo" viewBox="0 0 16 16"
														style="width: 3vw; height: 2.2vw; color: white;">
				<path fill-rule="evenodd"
															d="M2.5 12a.5.5 0 0 1 .5-.5h10a.5.5 0 0 1 0 1H3a.5.5 0 0 1-.5-.5m0-4a.5.5 0 0 1 .5-.5h10a.5.5 0 0 1 0 1H3a.5.5 0 0 1-.5-.5m0-4a.5.5 0 0 1 .5-.5h10a.5.5 0 0 1 0 1H3a.5.5 0 0 1-.5-.5" />
			</svg>
												</button>
												<ul class="dropdown-menu dropdown-menu-dark"
													style="min-width: 12vw; font-size: 1.2vw; background-color: #003300; border-radius: 5px;">
													<li><a class="dropdown-item seccion-bloqueada"
														href="#" style="color: white; background-color: #005500;">Idioma
															<span class="tooltip-text">Sección en desarrollo</span>
													</a></li>
													<li><a class="dropdown-item seccion-bloqueada"
														href="#" style="color: white;">Ayuda <span
															class="tooltip-text">Sección en desarrollo</span></a></li>
													<li><a class="dropdown-item seccion-bloqueada"
														href="#" style="color: white;">Configuración <span
															class="tooltip-text">Sección en desarrollo</span></a></li>
													<li>
														<hr class="dropdown-divider"
															style="border-color: #006600;">
													</li>
													<li><a class="dropdown-item" href="logout"
														style="color: white;">Cerrar sesión</a></li>
												</ul>
											</div>
										</div>

									</div>
								</div>
							</div>
						</div>
					</div>
				</div>

				<!-- HEADER COL -->

				<div class="  d-sm-none d-md-none col-12 d-block ">
					<div class="row align-items-stretch" style="height: 55px;">
						<!-- columna logo -->
						<div class="d-sm-none d-md-none col-2 d-block logo  "
							style="background-color: white; border: 2px solid black; border-top: none">
							<img src="${pageContext.request.contextPath}/Imagenes/LOGOWEB.PNG"></img>
						</div>

						<!-- Columna derecha que se divide en 2 filas -->
						<div class="d-sm-none d-md-none col-10 d-block ">
							<div class="row ">

								<!-- fila arriba -->
								<div class="d-sm-none d-md-none col-12 d-block"
									style="border: 2px solid black; border-bottom: none; border-left: none; border-top: 1px solid black;">
									<div class="row">
										<div
											class="d-sm-none d-md-none col-8 d-block cabeceraMedioTituloX">
											<span style="color: #d4af37; margin-right: 1.5vw;"> /
											</span> FUTBOL DE BARRIO <span
												style="color: #c0c0c0; margin-left: 7px;"> /</span>
										</div>
										<div
											class="d-sm-none d-md-none col-4 d-block cabeceraMedio  d-flex justify-content-center align-items-center">
											<%
											if (esPremium != null && esPremium) {
											%>
											<div class="premium-badge">
												<svg xmlns="http://www.w3.org/2000/svg" fill="currentColor"
													class="bi bi-star-fill" viewBox="0 0 16 16">
            <path
														d="M3.612 15.443c-.386.198-.824-.149-.746-.592l.83-4.73L.173 6.765c-.329-.32-.158-.888.283-.95l4.898-.696 2.194-4.445c.197-.398.73-.398.927 0l2.194 4.445 4.898.696c.441.062.612.63.283.95l-3.523 3.356.83 4.73c.078.443-.36.79-.746.592L8 13.187l-4.389 2.256z" />
          </svg>
												PREMIUM
											</div>
											<%
											} else {
											%>
											<a href="PagoPremium.jsp">
												<button type="button" class=" botonPremiumCabecera"
													title="Accede a todas las funciones premium y ventajas exclusivas">
													HAZTE PREMIUM</button>
											</a>
											<%
											}
											%>
										</div>

									</div>
								</div>
							</div>


							<!-- fila de abajo -->
							<div class="row p-1"
								style="border-bottom: solid 2.4px black; border-top: solid 2px black; border-right: solid 2px black; border-left: none; background-color: #004000; box-shadow: 0px 4px 8px -4px rgba(0, 0, 0, 0.45);">

								<!-- columna izquierda: INICIO -->
								<div
									class="col-3 d-flex justify-content-start align-items-center ps-4 ">
									<a href="Club.jsp" class="letraCabeceraAbajo "
										style="text-decoration: none; font-size: 2.5vw;">INICIO</a>
								</div>
								<div
									class="col-4 d-flex justify-content-start align-items-center ps-2 ">
									<a href="MarcadoresClub.jsp" class="letraCabeceraAbajo "
										style="text-decoration: none; font-size: 2.5vw;">MARCADORES</a>
								</div>
								<div
									class="col-3 d-flex justify-content-start align-items-center ps-2 ">
									<a href="EventoClub.jsp" class="letraCabeceraAbajo "
										style="text-decoration: none; font-size: 2.5vw; color: #d4af37;">EVENTOS</a>
								</div>



								<!-- columna derecha: menú desplegable -->
								<div
									class="col-2 d-flex justify-content-end align-items-center pe-4">
									<div class="dropdown">
										<button class="btn btn-secondary " type="button"
											data-bs-toggle="dropdown" aria-expanded="false"
											style="background-color: #004000; width: 7.5vw; height: 3.9vw; border-radius: 5px; display: flex; justify-content: center; align-items: center; padding: 0;">
											<svg xmlns="http://www.w3.org/2000/svg" fill="currentColor"
												class="bi bi-list letraCabeceraAbajo" viewBox="0 0 16 16"
												style="width: 6.5vw; height: 2.5vw; color: white;">
										<path fill-rule="evenodd"
													d="M2.5 12a.5.5 0 0 1 .5-.5h10a.5.5 0 0 1 0 1H3a.5.5 0 0 1-.5-.5m0-4a.5.5 0 0 1 .5-.5h10a.5.5 0 0 1 0 1H3a.5.5 0 0 1-.5-.5m0-4a.5.5 0 0 1 .5-.5h10a.5.5 0 0 1 0 1H3a.5.5 0 0 1-.5-.5" />
									</svg>
										</button>
										<ul class="dropdown-menu dropdown-menu-dark"
											style="min-width: 12vw; font-size: 2.2vw; background-color: #003300; border-radius: 5px; width: 25vw">


											<li><a class="dropdown-item seccion-bloqueada"
												href="Jugador.jsp">Alquileres <span class="tooltip-text">Sección
														en desarrollo</span></a></li>
											<li><a class="dropdown-item " href="PlantillaClub.jsp"
												style="color: white;">Plantilla </a></li>
											<li><a class="dropdown-item seccion-bloqueada"
												href="Jugador.jsp">Desafios <span class="tooltip-text">Sección
														en desarrollo</span></a></li>


											<li>
												<hr class="dropdown-divider" style="border-color: #006600;">
											</li>
											<li><a class="dropdown-item seccion-bloqueada" href="#">Idioma
													<span class="tooltip-text">Sección en desarrollo</span>
											</a></li>
											<li><a class="dropdown-item seccion-bloqueada" href="#">Ayuda
													<span class="tooltip-text">Sección en desarrollo</span>
											</a></li>
											<li><a class="dropdown-item seccion-bloqueada" href="#">Configuración
													<span class="tooltip-text">Sección en desarrollo</span>
											</a></li>

											<li>
												<hr class="dropdown-divider" style="border-color: #006600;">
											</li>
											<li><a class="dropdown-item" href="logout"
												style="color: white;">Cerrar sesión</a></li>

										</ul>
									</div>
								</div>

							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</header>



	<main style="background-color: rgba(223, 234, 213, 0.5);">
		<div class="container-fluid">
			<div class="row">
				<!-- CONTENIDO -->
				<div class="col-md-12 col-sm-12 col-12 mt-3">


					<!-- CONTENEDOR TORNEOS -->
					<div class="container-fluid " id="marcadorContainer">
						<div class="row">
							<div class="col-md-12 col-sm-12 col-12 mx-auto">
								<div class="row">

									<div class="col-md-2 col-sm-2 col-2 mx-auto"></div>
									<div class="col-md-3 col-sm-3 col-3 mx-auto "
										style="margin-bottom: 16vh; margin-top: 4vh; display: flex; justify-content: flex-end; align-items: center;">

										<button id="botonLiga"
											class="botonMarcadores p-4 en-desarrollo">
											<img class="imagenMarcadores" src="${pageContext.request.contextPath}/Imagenes/Liga.JPG"
												alt="Imagen la liga"> LIGA<span
												class="badge-desarrollo">En desarrollo</span>
										</button>
									</div>

									<!-- Espacio vacío entre los dos botones -->
									<div class="col-md-1 col-sm-1 col-1 mx-auto "></div>

									<!-- Columna para el botón de JUGADORES alineado a la derecha -->
									<div class="col-md-3 col-sm-3 col-3"
										style="margin-bottom: 16vh; margin-top: 4vh; display: flex; justify-content: flex-start; align-items: center;">
										<button class="botonMarcadores p-4" id="botonTorneo">
											<img class="imagenMarcadores" src="${pageContext.request.contextPath}/Imagenes/copa.JPG"
												alt="Imagen torneo"> TORNEO
										</button>
									</div>
									<div class="col-md-2 col-sm-2 col-2 mx-auto "></div>
								</div>
							</div>
						</div>
					</div>






					<!-- EVENTO Torneo -->
					<div class="container-fluid">
						<div class="row">
							<div class="col-md-12 col-sm-12 col-12 mx-auto"
								id="torneoContainer" style="display: none;">
								<!-- Botones de control -->
								<div
									class="d-flex justify-content-between align-items-center mb-3">
									<div class="col-md-11 col-sm-12 col-12 mx-auto   m-1"
										style="display: flex; align-items: center; gap: 10px;">
										<button id="volverAContenidoC" class=" mr-auto botonFiltrar"
											style="background-color: red">Volver</button>
										<button id="mostrarFiltrosTorneo"
											class=" mr-auto botonFiltrar" style="background-color: black">Mostrar
											Filtros</button>



									</div>
								</div>


								<!-- Filtros de búsqueda -->
								<div id="filtrosEventos" class="filaFiltrar mb-4"
									style="display: none;">
									<div class="filtroItem">
										<label class="labelFiltrar"><b>- Buscar por
												Nombre:</b></label> <input type="text" id="buscarEventoNombre"
											class="inputFiltrar" placeholder="Nombre torneo">
									</div>

									<div class="filtroItem">
										<label for="buscarModalidad" class="labelFiltrar"><b>-Buscar
												por Modalidad:</b></label> <input type="text" id="buscarModalidad"
											class="inputFiltrar" placeholder="Buscar por Modalidad">
									</div>

									<div class="filtroItem">
										<label class="labelFiltrar"><b>- Buscar por
												Estado:</b></label> <input type="text" id="buscarEventoEstado"
											class="inputFiltrar" placeholder="Abierto / Cerrado">
									</div>
								</div>

								<!-- Tabla de torneo -->
								<table
									class="tablaAdmin tablaAdmin--instalaciones w-100 mb-3 mx-auto">
									<thead class="tablaAdmin__head">
										<tr>
											<th>NOMBRE TORNEO</th>
											<th>INSTALACION</th>
											<th>DIRECCION</th>
											<th>MODALIDAD</th>
											<th>F.INICIO</th>
											<th>PLAZAS</th>
											<th>ESTADO</th>
											<th class="tablaAdmin__opciones">OPCIONES</th>
										</tr>
									</thead>
									<tbody id="tablaCuerpoTorneo"></tbody>
								</table>


								<!-- ✅ MISMA PAGINACIÓN QUE LA OTRA -->
								<div id="tablaCuerpoTorneo-paginacion"
									class="contenedorPaginacion mb-4 d-flex justify-content-center"></div>

							</div>
						</div>
					</div>


				</div>
			</div>
		</div>
	</main>


	<footer>

		<div class="container-fluid ">
			<div class="row">
				<div class="col-md-12 col-sm-12 d-none d-md-block d-sm-block">
					<div class="row">
						<div class="col-md-3 col-sm-3  g-4 pieDePagina p-4 pb-1  "
							style="margin-left: 2vw">
							<p style="text-decoration: underline; font-size: 1.5vw">SOBRE
								NOSOTROS</p>
							<p style="font-size: 1.05vw">Somos la plataforma líder para
								conectar jugadores, organizar torneos y reservar canchas de
								fútbol amateur. Únete a nuestra comunidad deportiva y vive la
								pasión por el fútbol.</p>
						</div>
						<div class="col-md-3 col-sm-3 g-4 pieDePagina p-4 pb-1 ">
							<p
								style="text-decoration: underline; font-family: 'Open Sans', sans-serif; font-size: 1.5vw">ENLACES
								UTILES</p>
							<pre
								style="text-decoration: underline; font-family: 'Open Sans', sans-serif; font-size: 1.05vw">
<a href="" style="color: white">Inicio</a>
<a href="" style="color: white">¿Qué es nuestra web?</a>
<a href="" style="color: white">Próximos eventos</a>
<a href="" style="color: white">Registrarse</a>
<a href="" style="color: white">Términos y Condiciones</a>
<a href="" style="color: white">Política de Privacidad</a> </pre>
						</div>
						<div class="col-md-3 col-sm-3 g-4 pieDePagina p-4 pb-1">
							<p style="text-decoration: underline; font-size: 1.5vw">CONTACTO</p>
							<pre
								style="font-family: 'Open Sans', sans-serif; font-size: 1.05vw">futboldebarrio@gmail.es
+34 123 456 789
Avenida mujer trabajadora
 Sevilla, España</pre>
						</div>
						<div class="col-md-2 col-sm-2 g-4 pieDePagina p-4 pb-1">
							<p
								style="text-decoration: underline; font-family: 'Open Sans', sans-serif; font-size: 1.5vw">
								SIGUENOS</p>


							<a href="" style="text-decoration: none"><svg
									xmlns="http://www.w3.org/2000/svg" width="1.8vw" height="1.6vw"
									fill="currentColor" style="color: white; margin-right: 0.5vw"
									class="bi bi-instagram" viewBox="0 0 16 16">
									<path
										d="M8 0C5.829 0 5.556.01 4.703.048 3.85.088 3.269.222 2.76.42a3.9 3.9 0 0 0-1.417.923A3.9 3.9 0 0 0 .42 2.76C.222 3.268.087 3.85.048 4.7.01 5.555 0 5.827 0 8.001c0 2.172.01 2.444.048 3.297.04.852.174 1.433.372 1.942.205.526.478.972.923 1.417.444.445.89.719 1.416.923.51.198 1.09.333 1.942.372C5.555 15.99 5.827 16 8 16s2.444-.01 3.298-.048c.851-.04 1.434-.174 1.943-.372a3.9 3.9 0 0 0 1.416-.923c.445-.445.718-.891.923-1.417.197-.509.332-1.09.372-1.942C15.99 10.445 16 10.173 16 8s-.01-2.445-.048-3.299c-.04-.851-.175-1.433-.372-1.941a3.9 3.9 0 0 0-.923-1.417A3.9 3.9 0 0 0 13.24.42c-.51-.198-1.092-.333-1.943-.372C10.443.01 10.172 0 7.998 0zm-.717 1.442h.718c2.136 0 2.389.007 3.232.046.78.035 1.204.166 1.486.275.373.145.64.319.92.599s.453.546.598.92c.11.281.24.705.275 1.485.039.843.047 1.096.047 3.231s-.008 2.389-.047 3.232c-.035.78-.166 1.203-.275 1.485a2.5 2.5 0 0 1-.599.919c-.28.28-.546.453-.92.598-.28.11-.704.24-1.485.276-.843.038-1.096.047-3.232.047s-2.39-.009-3.233-.047c-.78-.036-1.203-.166-1.485-.276a2.5 2.5 0 0 1-.92-.598 2.5 2.5 0 0 1-.6-.92c-.109-.281-.24-.705-.275-1.485-.038-.843-.046-1.096-.046-3.233s.008-2.388.046-3.231c.036-.78.166-1.204.276-1.486.145-.373.319-.64.599-.92s.546-.453.92-.598c.282-.11.705-.24 1.485-.276.738-.034 1.024-.044 2.515-.045zm4.988 1.328a.96.96 0 1 0 0 1.92.96.96 0 0 0 0-1.92m-4.27 1.122a4.109 4.109 0 1 0 0 8.217 4.109 4.109 0 0 0 0-8.217m0 1.441a2.667 2.667 0 1 1 0 5.334 2.667 2.667 0 0 1 0-5.334" />
</svg> </a> <a href="" style="text-decoration: none"><svg
									xmlns="http://www.w3.org/2000/svg" width="1.8vw" height="1.6vw"
									fill="currentColor"
									style="color: white; margin-right: 0.5vw; text-decoration: none"
									class="bi bi-twitter-x" viewBox="0 0 16 16">
  <path
										d="M12.6.75h2.454l-5.36 6.142L16 15.25h-4.937l-3.867-5.07-4.425 5.07H.316l5.733-6.57L0 .75h5.063l3.495 4.633L12.601.75Zm-.86 13.028h1.36L4.323 2.145H2.865z" />
</svg> </a> <a href="" style="text-decoration: none"><svg
									xmlns="http://www.w3.org/2000/svg" width="1.8vw" height="1.8vw"
									fill="currentColor" style="color: white; margin-right: 0.5vw;"
									class="bi bi-youtube" viewBox="0 0 16 16">
  <path
										d="M8.051 1.999h.089c.822.003 4.987.033 6.11.335a2.01 2.01 0 0 1 1.415 1.42c.101.38.172.883.22 1.402l.01.104.022.26.008.104c.065.914.073 1.77.074 1.957v.075c-.001.194-.01 1.108-.082 2.06l-.008.105-.009.104c-.05.572-.124 1.14-.235 1.558a2.01 2.01 0 0 1-1.415 1.42c-1.16.312-5.569.334-6.18.335h-.142c-.309 0-1.587-.006-2.927-.052l-.17-.006-.087-.004-.171-.007-.171-.007c-1.11-.049-2.167-.128-2.654-.26a2.01 2.01 0 0 1-1.415-1.419c-.111-.417-.185-.986-.235-1.558L.09 9.82l-.008-.104A31 31 0 0 1 0 7.68v-.123c.002-.215.01-.958.064-1.778l.007-.103.003-.052.008-.104.022-.26.01-.104c.048-.519.119-1.023.22-1.402a2.01 2.01 0 0 1 1.415-1.42c.487-.13 1.544-.21 2.654-.26l.17-.007.172-.006.086-.003.171-.007A100 100 0 0 1 7.858 2zM6.4 5.209v4.818l4.157-2.408z" />
</svg> </a> <a href="" style="text-decoration: none"> <svg
									xmlns="http://www.w3.org/2000/svg" width="1.8vw" height="1.6vw"
									fill="currentColor" style="color: white; margin-right: 0.5vw"
									class="bi bi-facebook" viewBox="0 0 16 16">
  <path
										d="M16 8.049c0-4.446-3.582-8.05-8-8.05C3.58 0-.002 3.603-.002 8.05c0 4.017 2.926 7.347 6.75 7.951v-5.625h-2.03V8.05H6.75V6.275c0-2.017 1.195-3.131 3.022-3.131.876 0 1.791.157 1.791.157v1.98h-1.009c-.993 0-1.303.621-1.303 1.258v1.51h2.218l-.354 2.326H9.25V16c3.824-.604 6.75-3.934 6.75-7.951" />
</svg>
							</a> <a href="" style="text-decoration: none"> <svg
									xmlns="http://www.w3.org/2000/svg" width="1.8vw" height="1.6vw"
									fill="currentColor" style="color: white;" class="bi bi-tiktok"
									viewBox="0 0 16 16">
  <path
										d="M9 0h1.98c.144.715.54 1.617 1.235 2.512C12.895 3.389 13.797 4 15 4v2c-1.753 0-3.07-.814-4-1.829V11a5 5 0 1 1-5-5v2a3 3 0 1 0 3 3z" />
</svg>
							</a>



						</div>
					</div>

				</div>
				<div
					class="col-md-12 col-sm-12 d-none d-md-block d-sm-block pieDePagina p-1 mt-4"
					style="border-top: 1px solid white;">
					<p style="font-size: 1.8vw; margin-top: 1.4vw; color: #d4af37">©
						2024 futboldebarrio.com | Todos los derechos reservados</p>

				</div>




				<div class=" col-12 d-block d-md-none d-sm-none">
					<div class="row">

						<div
							class=" col-4 d-block d-md-none d-sm-none g-2 pieDePagina p-2  ">
							<p
								style="text-decoration: underline; font-family: 'Open Sans', sans-serif; font-size: 2vw">ENLACES
								UTILES</p>
							<pre
								style="text-decoration: underline; font-family: 'Open Sans', sans-serif; font-size: 1.6vw">
<a href="" style="color: white">Inicio</a>
<a href="" style="color: white">¿Qué es nuestra web?</a>
<a href="" style="color: white">Próximos eventos</a>
<a href="" style="color: white">Registrarse</a>
<a href="" style="color: white">Términos y Condiciones</a>
<a href="" style="color: white">Política de Privacidad</a> </pre>
						</div>
						<div
							class="col-4 d-block d-md-none d-sm-none g-2 pieDePagina p-2 ">
							<p style="text-decoration: underline; font-size: 2vw">CONTACTO</p>
							<pre
								style="font-family: 'Open Sans', sans-serif; font-size: 1.6vw">futboldebarrio@gmail.es
+34 123 456 789
Avenida mujer trabajadora
 Sevilla, España</pre>
						</div>
						<div
							class="col-4 d-block d-md-none d-sm-none g-2 pieDePagina p-2 ">
							<p
								style="text-decoration: underline; font-family: 'Open Sans', sans-serif; font-size: 2vw">
								SIGUENOS</p>


							<a href="" style="text-decoration: none"><svg
									xmlns="http://www.w3.org/2000/svg" width="2.2vw" height="2vw"
									fill="currentColor" style="color: white; margin-right: 0.5vw"
									class="bi bi-instagram" viewBox="0 0 16 16">
									<path
										d="M8 0C5.829 0 5.556.01 4.703.048 3.85.088 3.269.222 2.76.42a3.9 3.9 0 0 0-1.417.923A3.9 3.9 0 0 0 .42 2.76C.222 3.268.087 3.85.048 4.7.01 5.555 0 5.827 0 8.001c0 2.172.01 2.444.048 3.297.04.852.174 1.433.372 1.942.205.526.478.972.923 1.417.444.445.89.719 1.416.923.51.198 1.09.333 1.942.372C5.555 15.99 5.827 16 8 16s2.444-.01 3.298-.048c.851-.04 1.434-.174 1.943-.372a3.9 3.9 0 0 0 1.416-.923c.445-.445.718-.891.923-1.417.197-.509.332-1.09.372-1.942C15.99 10.445 16 10.173 16 8s-.01-2.445-.048-3.299c-.04-.851-.175-1.433-.372-1.941a3.9 3.9 0 0 0-.923-1.417A3.9 3.9 0 0 0 13.24.42c-.51-.198-1.092-.333-1.943-.372C10.443.01 10.172 0 7.998 0zm-.717 1.442h.718c2.136 0 2.389.007 3.232.046.78.035 1.204.166 1.486.275.373.145.64.319.92.599s.453.546.598.92c.11.281.24.705.275 1.485.039.843.047 1.096.047 3.231s-.008 2.389-.047 3.232c-.035.78-.166 1.203-.275 1.485a2.5 2.5 0 0 1-.599.919c-.28.28-.546.453-.92.598-.28.11-.704.24-1.485.276-.843.038-1.096.047-3.232.047s-2.39-.009-3.233-.047c-.78-.036-1.203-.166-1.485-.276a2.5 2.5 0 0 1-.92-.598 2.5 2.5 0 0 1-.6-.92c-.109-.281-.24-.705-.275-1.485-.038-.843-.046-1.096-.046-3.233s.008-2.388.046-3.231c.036-.78.166-1.204.276-1.486.145-.373.319-.64.599-.92s.546-.453.92-.598c.282-.11.705-.24 1.485-.276.738-.034 1.024-.044 2.515-.045zm4.988 1.328a.96.96 0 1 0 0 1.92.96.96 0 0 0 0-1.92m-4.27 1.122a4.109 4.109 0 1 0 0 8.217 4.109 4.109 0 0 0 0-8.217m0 1.441a2.667 2.667 0 1 1 0 5.334 2.667 2.667 0 0 1 0-5.334" />
</svg> </a> <a href="" style="text-decoration: none"><svg
									xmlns="http://www.w3.org/2000/svg" width="2.2vw" height="2vw"
									fill="currentColor"
									style="color: white; margin-right: 0.5vw; text-decoration: none"
									class="bi bi-twitter-x" viewBox="0 0 16 16">
  <path
										d="M12.6.75h2.454l-5.36 6.142L16 15.25h-4.937l-3.867-5.07-4.425 5.07H.316l5.733-6.57L0 .75h5.063l3.495 4.633L12.601.75Zm-.86 13.028h1.36L4.323 2.145H2.865z" />
</svg> </a> <a href="" style="text-decoration: none"><svg
									xmlns="http://www.w3.org/2000/svg" width="2.2vw" height="2vw"
									fill="currentColor" style="color: white; margin-right: 0.5vw;"
									class="bi bi-youtube" viewBox="0 0 16 16">
  <path
										d="M8.051 1.999h.089c.822.003 4.987.033 6.11.335a2.01 2.01 0 0 1 1.415 1.42c.101.38.172.883.22 1.402l.01.104.022.26.008.104c.065.914.073 1.77.074 1.957v.075c-.001.194-.01 1.108-.082 2.06l-.008.105-.009.104c-.05.572-.124 1.14-.235 1.558a2.01 2.01 0 0 1-1.415 1.42c-1.16.312-5.569.334-6.18.335h-.142c-.309 0-1.587-.006-2.927-.052l-.17-.006-.087-.004-.171-.007-.171-.007c-1.11-.049-2.167-.128-2.654-.26a2.01 2.01 0 0 1-1.415-1.419c-.111-.417-.185-.986-.235-1.558L.09 9.82l-.008-.104A31 31 0 0 1 0 7.68v-.123c.002-.215.01-.958.064-1.778l.007-.103.003-.052.008-.104.022-.26.01-.104c.048-.519.119-1.023.22-1.402a2.01 2.01 0 0 1 1.415-1.42c.487-.13 1.544-.21 2.654-.26l.17-.007.172-.006.086-.003.171-.007A100 100 0 0 1 7.858 2zM6.4 5.209v4.818l4.157-2.408z" />
</svg> </a> <a href="" style="text-decoration: none"> <svg
									xmlns="http://www.w3.org/2000/svg" width="2.2vw" height="2vw"
									fill="currentColor" style="color: white; margin-right: 0.5vw"
									class="bi bi-facebook" viewBox="0 0 16 16">
  <path
										d="M16 8.049c0-4.446-3.582-8.05-8-8.05C3.58 0-.002 3.603-.002 8.05c0 4.017 2.926 7.347 6.75 7.951v-5.625h-2.03V8.05H6.75V6.275c0-2.017 1.195-3.131 3.022-3.131.876 0 1.791.157 1.791.157v1.98h-1.009c-.993 0-1.303.621-1.303 1.258v1.51h2.218l-.354 2.326H9.25V16c3.824-.604 6.75-3.934 6.75-7.951" />
</svg>
							</a> <a href="" style="text-decoration: none"> <svg
									xmlns="http://www.w3.org/2000/svg" width="2.2vw" height="2vw"
									fill="currentColor" style="color: white;" class="bi bi-tiktok"
									viewBox="0 0 16 16">
  <path
										d="M9 0h1.98c.144.715.54 1.617 1.235 2.512C12.895 3.389 13.797 4 15 4v2c-1.753 0-3.07-.814-4-1.829V11a5 5 0 1 1-5-5v2a3 3 0 1 0 3 3z" />
</svg>
							</a>



						</div>
					</div>




				</div>
				<div class="col-12 d-block d-md-none d-sm-none  pieDePagina p-1 "
					style="border-top: 1px solid white;">
					<p style="font-size: 2.4vw; margin-top: 1.4vw; color: #d4af37">©
						2024 futboldebarrio.com | Todos los derechos reservados</p>

				</div>
			</div>
		</div>

	</footer>

	<script>
window.onload = function () {
    setTimeout(function () {
        const mensajes = document.querySelectorAll('.fade-message');
        mensajes.forEach(function (el) {
            el.style.transition = "opacity 1s ease-out";
            el.style.opacity = '0';
            setTimeout(function() { el.remove(); }, 1000); // elimina el elemento del DOM tras ocultarlo
        });
    }, 2000); // Espera 2 segundos antes de ocultarlo
};

// Botón Torneo
document.getElementById("botonTorneo").addEventListener("click", function() {
    document.getElementById("botonLiga").parentElement.style.display = "none";
    document.getElementById("botonTorneo").parentElement.style.display = "none";
    document.getElementById("torneoContainer").style.display = "block";
});

// Mostrar u ocultar filtros
document.getElementById("mostrarFiltrosTorneo").addEventListener("click", function() {
    const filtros = document.getElementById("filtrosEventos");

    if (!filtros) return; // seguridad extra

    if (filtros.style.display === "none") {
        filtros.style.display = "flex";
        this.textContent = "Ocultar Filtros";
    } else {
        filtros.style.display = "none";
        this.textContent = "Mostrar Filtros";
    }
});


// Función para filtrar la tabla
function filtrarTabla(idFiltro, columnaIndex) {
    const valorFiltro = document.getElementById(idFiltro).value.toLowerCase();
    const filas = document.querySelectorAll(".tablaDatos tbody tr");

    filas.forEach(function(fila) {
        const celdas = fila.getElementsByTagName("td");
        const textoCelda = celdas[columnaIndex].textContent.toLowerCase();
        fila.style.display = textoCelda.includes(valorFiltro) ? "" : "none";
    });
}

// Eventos de filtro
let torneosJugador = [];

document.getElementById("buscarEventoNombre")
.addEventListener("input", filtrarEventos);

document.getElementById("buscarModalidad")
.addEventListener("input", filtrarEventos);

document.getElementById("buscarEventoEstado")
.addEventListener("input", filtrarEventos);





// Volver a la vista anterior
document.getElementById("volverAContenidoC").addEventListener("click", function() {
    document.getElementById("torneoContainer").style.display = "none";
    document.getElementById("botonTorneo").parentElement.style.display = "flex";
    document.getElementById("botonLiga").parentElement.style.display = "flex";
});

// jQuery para cargar torneos y delegar eventos
$(document).ready(function() {
    cargarTorneos();

    // Delegación para abrir detalles de torneo
    $('#tablaCuerpoTorneo').on('click', '.torneoLink', function() {
        var torneoId = $(this).data('id');
        if (torneoId) {
            window.location.href = 'DetallesTorneo.jsp?idTorneo=' + torneoId;
        } else {
            alert("No se encontró el ID del torneo.");
        }
    });

    // Delegación para unirse al torneo
    $('#tablaCuerpoTorneo').on('click', '.icono-unirse', function() {
        var torneoId = $(this).data('torneo-id');
        var clubId = '<%=clubId%>';

												if (!clubId) {
													alert("No se pudo obtener el ID del club.");
													return;
												}

												$
														.ajax({
													        url: '<%=request.getContextPath()%>/club/eventos',
															method : 'POST',
															data : {
																torneoId : torneoId
															},
															success : function(
																	response) {
																alert(response);
																console
																		.log(
																				"Éxito:",
																				response);
															},
															error : function(
																	xhr) {
																alert("Error: "
																		+ xhr.responseText);
																console
																		.error(
																				"Error:",
																				xhr.status,
																				xhr.responseText);
															}
														});
											});
						});

		// Función para cargar torneos en la tabla
		function cargarTorneos() {
    $.ajax({
        url: '<%=request.getContextPath()%>/club/eventos',
        method: 'GET',
        dataType: 'json',
        xhrFields: { withCredentials: true },
        success: function(data) {
        	
            var tbody = document.getElementById("tablaCuerpoTorneo");
            tbody.innerHTML = "";

            if (data && Array.isArray(data) && data.length > 0) {

                data.forEach(function(torneo) {

                    var nombreHTML = torneo.estaActivo
                        ? '<span class="torneoLink" data-id="' + torneo.idTorneo + '" style="cursor:pointer; color:green; text-decoration:underline; font-weight:bold;">' 
                          + torneo.nombreTorneo + 
                          '</span>'
                        : torneo.nombreTorneo;

                    var iconoUnirse = torneo.estaActivo 
                        ? '<i class="fas fa-lock" title="Torneo activo" style="color:gray;"></i>'
                        : '<i class="fas fa-right-to-bracket icono-unirse" data-torneo-id="' + torneo.idTorneo + '" title="Unirse al torneo" style="cursor:pointer; color:green;"></i>';

                    var fila = document.createElement("tr");
                    fila.id = "fila-" + torneo.idTorneo;

                    fila.innerHTML =
                        "<td class='tablaAdmin__id'>" + nombreHTML + "</td>" +
                        "<td>" + (torneo.nombreInstalacion || "") + "</td>" +
                        "<td>" + (torneo.direccionInstalacion || "") + "</td>" +
                        "<td>" + (torneo.modalidad || "") + "</td>" +
                        "<td>" + (torneo.fechaInicioTorneo || "") + "</td>" +
                        "<td style='color:green;'>" + (torneo.clubesInscritos || "0 / 0") + "</td>" +
                        "<td>" + (torneo.estaActivo 
                                    ? '<span style="color:red;font-weight:bold;">Cerrado</span>' 
                                    : '<span style="color:green;font-weight:bold;">Disponible</span>') 
                        + "</td>" +
                        "<td class='tablaAdmin__opciones'>" +
                            "<div class='tablaAdmin__acciones'>" +
                                iconoUnirse +
                            "</div>" +
                        "</td>";

                    tbody.appendChild(fila);
                });

                paginarTabla("tablaCuerpoTorneo", 8);

            } else {
                tbody.innerHTML = '<tr><td colspan="8">No hay torneos disponibles</td></tr>';
            }
        },
        error: function(xhr) {
            console.error("Error cargando torneos:", xhr.status, xhr.responseText);
            document.getElementById("tablaCuerpoTorneo").innerHTML = '<tr><td colspan="8">Error cargando torneos</td></tr>';
        }
    });
}

		
		function paginarTabla(tablaBodyId, filasPorPagina = 8) {
		    const tbody = document.getElementById(tablaBodyId);
		    if (!tbody) return;

		    // Todas las filas actuales (ya añadidas dinámicamente)
		    const todasFilas = Array.from(tbody.querySelectorAll('tr'));
		    // Si no hay filas, limpiamos paginación
		    const pagDiv = document.getElementById(tablaBodyId + '-paginacion');
		    if (!pagDiv) return;
		    if (todasFilas.length === 0) {
		        pagDiv.innerHTML = '';
		        return;
		    }

		    // Sólo consideramos filas visibles (si aplicas filtros que oculten filas)
		    const filasVisibles = todasFilas.filter(f => f.style.display !== 'none');
		    const totalPaginas = Math.max(1, Math.ceil(filasVisibles.length / filasPorPagina));
		    let paginaActual = 1;

		    // Crea los elementos UI (se regeneran cada vez para reflejar actualizaciones)
		    function renderControles() {
		        pagDiv.innerHTML = '';

		        // Botón Anterior
		        const btnAnterior = document.createElement('button');
		        btnAnterior.type = 'button';
		        btnAnterior.textContent = '< Anterior';
		        btnAnterior.disabled = paginaActual === 1;
		        btnAnterior.className = 'paginacion';
		        btnAnterior.addEventListener('click', () => {
		            if (paginaActual > 1) {
		                paginaActual--;
		                mostrarPagina(paginaActual);
		            }
		        });

		        // Span info página
		        const spanInfo = document.createElement('span');
		        spanInfo.id = tablaBodyId + '-pagina-info';
		        spanInfo.textContent = ' Página ' + paginaActual + ' de ' + totalPaginas + ' ';
		        spanInfo.style.margin = '0 10px';
		        spanInfo.style.fontSize = '1.1vw';
		        spanInfo.style.display = 'inline-block';
		        spanInfo.style.alignSelf = 'center';

		        // Botón Siguiente
		        const btnSiguiente = document.createElement('button');
		        btnSiguiente.type = 'button';
		        btnSiguiente.textContent = 'Siguiente >';
		        btnSiguiente.disabled = paginaActual === totalPaginas;
		        btnSiguiente.className = 'paginacion';
		        btnSiguiente.addEventListener('click', () => {
		            if (paginaActual < totalPaginas) {
		                paginaActual++;
		                mostrarPagina(paginaActual);
		            }
		        });

		        // Estructura flex (mantener centrado)
		        pagDiv.style.display = 'flex';
		        pagDiv.style.justifyContent = 'center';
		        pagDiv.style.alignItems = 'center';
		        pagDiv.style.gap = '0.8vw';

		        pagDiv.appendChild(btnAnterior);
		        pagDiv.appendChild(spanInfo);
		        pagDiv.appendChild(btnSiguiente);
		    }

		    // Muestra la página solicitada (1-based)
		    function mostrarPagina(pagina) {
		        if (pagina < 1) pagina = 1;
		        if (pagina > totalPaginas) pagina = totalPaginas;
		        paginaActual = pagina;

		        const inicio = (paginaActual - 1) * filasPorPagina;
		        const fin = inicio + filasPorPagina;

		        // Oculta/mostrar filas basadas en filasVisibles (preserva el orden original)
		        filasVisibles.forEach((fila, index) => {
		            fila.style.display = (index >= inicio && index < fin) ? '' : 'none';
		        });

		        // Actualiza controles
		        renderControles();
		    }

		    // Si el número total de filas visibles cambió (por ejemplo filtros), recalculamos y mostramos la 1ª página
		    mostrarPagina(1);
		}
		
		function filtrarEventos() {
		    const filtroNombre = document.getElementById("buscarEventoNombre").value.toLowerCase();
		    const filtroModalidad = document.getElementById("buscarModalidad").value.toLowerCase();
		    const filtroEstado = document.getElementById("buscarEventoEstado").value.toLowerCase();

		    const filas = document.querySelectorAll("#tablaCuerpoTorneo tr");

		    filas.forEach(fila => {
		        const celdas = fila.getElementsByTagName("td");

		        const nombre = celdas[0]?.textContent.toLowerCase() || "";
		        const modalidad = celdas[3]?.textContent.toLowerCase() || "";
		        const estado = celdas[6]?.textContent.toLowerCase() || "";

		        const coincide =
		            nombre.includes(filtroNombre) &&
		            modalidad.includes(filtroModalidad) &&
		            estado.includes(filtroEstado);

		        fila.style.display = coincide ? "" : "none";
		    });
		}

		function abrirGmail() {
			const email = "futboldebarrio@gmail.com";
			const subject = "Titulo del Asunto: ";
			const body = "Escriba aqui el mensaje....";

			const url = "https://mail.google.com/mail/?view=cm&fs=1&to="
					+ encodeURIComponent(email) + "&su="
					+ encodeURIComponent(subject) + "&body="
					+ encodeURIComponent(body);

			window.open(url, "_blank");
		}
	</script>
	<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
		crossorigin="anonymous"></script>
</body>
</html>