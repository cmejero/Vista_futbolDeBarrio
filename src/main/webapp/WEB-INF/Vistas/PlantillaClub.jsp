<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
    
    String tipoUsuario = ((String) session.getAttribute("tipoUsuario"));
    
    
    if (tipoUsuario == null || !"club".equals(tipoUsuario)) {
        response.sendRedirect("acceso_denegado.jsp");
        return; // Detener el renderizado de la página
    }

    Long clubId = (Long) session.getAttribute("clubId");
    String nombreUsuario = (String) session.getAttribute("nombreClub");
    if (nombreUsuario == null) nombreUsuario = "Invitado";

    Boolean esPremium = (Boolean) session.getAttribute("esPremium");
    if (esPremium == null) esPremium = false;
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
 <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
 
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
							<img
								src="${pageContext.request.contextPath}/Imagenes/LOGOWEB.PNG"></img>
						</div>

						<!-- Columna derecha que se divide en 2 filas -->
						<div class="col-sm-11 col-md-11">
							<div class="row ">
								<!-- fila arriba -->
								<div class="col-sm-12 col-md-12" style="border: solid 2px black">
									<div class="row">
										<div class="col-sm-3 col-md-3 cabeceraArriba p-2 "></div>
										<div class="col-sm-4 col-md-4 cabeceraArriba "
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
										<div class="col-sm-1 col-md-1 cabeceraArriba"></div>
										<div class="col-sm-2 col-md-2 cabeceraArriba "
											style="justify-content: left;">
											<a href="">
												<button type="button" class="botonCabeceraContactar"
													onclick="abrirGmail()">CONTACTAR</button>
											</a>
										</div>
										<div class="col-sm-2 col-md-2 cabeceraArriba">
											<a href="https://www.instagram.com/futboldebarriosevilla/">
												<svg xmlns="http://www.w3.org/2000/svg" width="1.4vw"
													height="1.2vw" fill="currentColor"
													style="margin-right: 3px; color: white;" bi bi-instagram
													viewBox="0 0 16 16">
	  <path
														d="M8 0C5.829 0 5.556.01 4.703.048 3.85.088 3.269.222 2.76.42a3.9 3.9 0 0 0-1.417.923A3.9 3.9 0 0 0 .42 2.76C.222 3.268.087 3.85.048 4.7.01 5.555 0 5.827 0 8.001c0 2.172.01 2.444.048 3.297.04.852.174 1.433.372 1.942.205.526.478.972.923 1.417.444.445.89.719 1.416.923.51.198 1.09.333 1.942.372C5.555 15.99 5.827 16 8 16s2.444-.01 3.298-.048c.851-.04 1.434-.174 1.943-.372a3.9 3.9 0 0 0 1.416-.923c.445-.445.718-.891.923-1.417.197-.509.332-1.09.372-1.942C15.99 10.445 16 10.173 16 8s-.01-2.445-.048-3.299c-.04-.851-.175-1.433-.372-1.941a3.9 3.9 0 0 0-.923-1.417A3.9 3.9 0 0 0 13.24.42c-.51-.198-1.092-.333-1.943-.372C10.443.01 10.172 0 7.998 0zm-.717 1.442h.718c2.136 0 2.389.007 3.232.046.78.035 1.204.166 1.486.275.373.145.64.319.92.599s.453.546.598.92c.11.281.24.705.275 1.485.039.843.047 1.096.047 3.231s-.008 2.389-.047 3.232c-.035.78-.166 1.203-.275 1.485a2.5 2.5 0 0 1-.599.919c-.28.28-.546.453-.92.598-.28.11-.704.24-1.485.276-.843.038-1.096.047-3.232.047s-2.39-.009-3.233-.047c-.78-.036-1.203-.166-1.485-.276a2.5 2.5 0 0 1-.92-.598 2.5 2.5 0 0 1-.6-.92c-.109-.281-.24-.705-.275-1.485-.038-.843-.046-1.096-.046-3.233s.008-2.388.046-3.231c.036-.78.166-1.204.276-1.486.145-.373.319-.64.599-.92s.546-.453.92-.598c.282-.11.705-.24 1.485-.276.738-.034 1.024-.044 2.515-.045zm4.988 1.328a.96.96 0 1 0 0 1.92.96.96 0 0 0 0-1.92m-4.27 1.122a4.109 4.109 0 1 0 0 8.217 4.109 4.109 0 0 0 0-8.217m0 1.441a2.667 2.667 0 1 1 0 5.334 2.667 2.667 0 0 1 0-5.334" />
	</svg>
											</a> <a href="https://x.com/FDB_Sevilla"> <svg
													xmlns="http://www.w3.org/2000/svg" width="1.4vw"
													height="1.2vw" fill="currentColor"
													style="margin-right: 3px; color: white;"
													class="bi bi-twitter-x" viewBox="0 0 16 16">
	  <path
														d="M12.6.75h2.454l-5.36 6.142L16 15.25h-4.937l-3.867-5.07-4.425 5.07H.316l5.733-6.57L0 .75h5.063l3.495 4.633L12.601.75Zm-.86 13.028h1.36L4.323 2.145H2.865z" />
	</svg>
											</a> <a
												href="https://www.youtube.com/channel/UCfzHaUblCQl7lzp4CHXR9ug">
												<svg xmlns="http://www.w3.org/2000/svg" width="1.4vw"
													height="1.4vw" fill="currentColor"
													style="margin-right: 3px; color: white;"
													class="bi bi-youtube" viewBox="0 0 16 16">
	  <path
														d="M8.051 1.999h.089c.822.003 4.987.033 6.11.335a2.01 2.01 0 0 1 1.415 1.42c.101.38.172.883.22 1.402l.01.104.022.26.008.104c.065.914.073 1.77.074 1.957v.075c-.001.194-.01 1.108-.082 2.06l-.008.105-.009.104c-.05.572-.124 1.14-.235 1.558a2.01 2.01 0 0 1-1.415 1.42c-1.16.312-5.569.334-6.18.335h-.142c-.309 0-1.587-.006-2.927-.052l-.17-.006-.087-.004-.171-.007-.171-.007c-1.11-.049-2.167-.128-2.654-.26a2.01 2.01 0 0 1-1.415-1.419c-.111-.417-.185-.986-.235-1.558L.09 9.82l-.008-.104A31 31 0 0 1 0 7.68v-.123c.002-.215.01-.958.064-1.778l.007-.103.003-.052.008-.104.022-.26.01-.104c.048-.519.119-1.023.22-1.402a2.01 2.01 0 0 1 1.415-1.42c.487-.13 1.544-.21 2.654-.26l.17-.007.172-.006.086-.003.171-.007A100 100 0 0 1 7.858 2zM6.4 5.209v4.818l4.157-2.408z" />																						
											</a> <a href="https://www.tiktok.com/@fdb_sevilla"> <svg
													xmlns="http://www.w3.org/2000/svg" width="1.4vw"
													height="1.2vw" fill="currentColor"
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
												<a href="${pageContext.request.contextPath}/pagoPremium">
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
											<a href="${pageContext.request.contextPath}/club" class="letraCabeceraAbajo"
												>INICIO</a>
										</div>
										<div class="col-sm-4 col-md-4 cabeceraAbajo ">
											<a href="" class="letraCabeceraAbajo seccion-bloqueada">ALQUILERES
												<span class="tooltip-text">Sección en desarrollo</span>
											</a>
										</div>
										<div class="col-sm-3 col-md-3 cabeceraAbajo ">
											<a href="${pageContext.request.contextPath}/club/eventos" class="letraCabeceraAbajo">EVENTOS</a>
										</div>
										<div class="col-sm-1 col-md-1 cabeceraAbajo"></div>

									</div>
								</div>

								<!-- columna derecha -->
								<div class="col-sm-7 col-md-7 ">

									<div class="row">
										<div class="col-sm-1 col-md-1 cabeceraAbajo "></div>
										<div class="col-sm-3 col-md-3 cabeceraAbajo ">
											<a href="${pageContext.request.contextPath}/club/plantilla" class="letraCabeceraAbajo" style="color: #d4af37;">PLANTILLA</a>
										</div>
										<div class="col-sm-3 col-md-3 cabeceraAbajo ">
											<a href="${pageContext.request.contextPath}/marcadores" class="letraCabeceraAbajo">MARCADORES</a>
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
													<li><a class="dropdown-item" href="${pageContext.request.contextPath}/logout"
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
											<a href="${pageContext.request.contextPath}/pagoPremium">
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
									<a href="${pageContext.request.contextPath}/club" class="letraCabeceraAbajo "
										style="text-decoration: none; font-size: 2.5vw; ">INICIO</a>
								</div>
								<div
									class="col-4 d-flex justify-content-start align-items-center ps-2 ">
									<a href="${pageContext.request.contextPath}/marcadores" class="letraCabeceraAbajo "
										style="text-decoration: none; font-size: 2.5vw;">MARCADORES</a>
								</div>
								<div
									class="col-3 d-flex justify-content-start align-items-center ps-2 ">
									<a href="${pageContext.request.contextPath}/club/plantilla" class="letraCabeceraAbajo "
										style="text-decoration: none; font-size: 2.5vw; color: #d4af37;">PLANTILLA</a>
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
												href="Jugador.jsp">Alquileres <span
													class="tooltip-text">Sección en desarrollo</span></a></li>
											<li><a class="dropdown-item " href="${pageContext.request.contextPath}/club/eventos"
												style="color: white;">Eventos </a></li>
											<li><a class="dropdown-item seccion-bloqueada"
												href="Jugador.jsp">Desafios <span
													class="tooltip-text">Sección en desarrollo</span></a></li>


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
											<li><a class="dropdown-item" href="${pageContext.request.contextPath}/logout"
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

    <div class="container-fluid" id="jugadorContainer">
        <div class="row">
            <!-- Contenedor para los botones -->
            <div class="col-md-12 col-sm-12 col-12 mx-auto pt-1 m-1"
                 style="display: flex; align-items: center; gap: 10px;">
                <button id="mostrarFiltrosJugadores"
                        class="mb-2 mr-auto botonFiltrar" style="background-color: black">
                    Mostrar Filtros
                </button>
            </div>

            <!-- Filtros -->
            <div id="filtrosJugadores" class="filaFiltrar"
                 style="background-color: black; display: none;">

                <div class="filtroItem">
                    <input type="text" id="buscarPosicionJugador" class="inputFiltrar"
                           placeholder="Buscar por posición">
                </div>
                <div class="filtroItem">
                    <input type="text" id="buscarNombreJugador" class="inputFiltrar"
                           placeholder="Buscar por Nombre">
                </div>
                <div class="filtroItem">
                    <input type="text" id="buscarAliasJugador" class="inputFiltrar"
                           placeholder="Buscar por Alias">
                </div>
            </div>

            <!-- Tabla -->
            <table class="tablaDatosListaMarcadores mb-4">
                <thead style="background-color: black;">
                <tr>
                    <th style="border: 1.5px solid red; width: 7.5%">POS</th>
                    <th style="border: 1.5px solid red; width: 37%">JUGADOR</th>
                    <th style="border: 1.5px solid red; width: 25.5%">ALIAS</th>
                    <th style="border: 1.5px solid red; width: 5%">PJ</th>
                    <th style="border: 1.5px solid red; width: 5%">G</th>
                    <th style="border: 1.5px solid red; width: 5%">V</th>
                    <th style="border: 1.5px solid red; width: 5%">D</th>
                    <th style="border: 1.5px solid red; width: 10%">ELIMINAR</th>
                </tr>
                </thead>
                <tbody id="tablaCuerpoJugador"></tbody>
            </table>
        </div>
    </div>
</main>

<footer>

		<div class="container-fluid ">
			<div class="row">
				<div class="col-md-12 col-sm-12 d-none d-md-block d-sm-block">
					<div class="row">
						<div class="col-md-3 col-sm-3  g-2 pieDePagina p-3 pb-1  "
							style="margin-left: 2vw">
							<p style="text-decoration: underline; font-size: 1.5vw">SOBRE
								NOSOTROS</p>
							<p style="font-size: 1.05vw">Somos una plataforma innovadora
								para conectar jugadores, organizar torneos y reservar campos de
								fútbol. Únete a nuestra comunidad deportiva y vive la pasión por
								el fútbol.</p>
						</div>
						<div class="col-md-3 col-sm-3 g-2 pieDePagina p-3 pb-1 ">
							<p
								style="text-decoration: underline; font-family: 'Open Sans', sans-serif; font-size: 1.5vw">ENLACES
								ÚTILES</p>
							<pre
								style="text-decoration: underline; font-family: 'Open Sans', sans-serif; font-size: 1.05vw">
<a href="${pageContext.request.contextPath}/pagoPremium" style="color: white">Hazte Premium</a>
<a href="${pageContext.request.contextPath}/club"
									style="color: white">Inicio</a>
<a href="${pageContext.request.contextPath}/club/plantilla"
									style="color: white">Plantilla</a>
<a href="${pageContext.request.contextPath}/marcadores"
									style="color: white">Marcadores</a>
<a href="${pageContext.request.contextPath}/club/eventos"
									style="color: white">Eventos</a></pre>
						</div>
						<div class="col-md-3 col-sm-3 g-2 pieDePagina p-3 pb-1">
							<p style="text-decoration: underline; font-size: 1.5vw">CONTACTO</p>
							<pre
								style="font-family: 'Open Sans', sans-serif; font-size: 1.05vw">futboldebarriosevilla@gmail.com
</pre>
						</div>	
						<div class="col-md-2 col-sm-2 g-2 pieDePagina p-3 pb-1">
							<p
								style="text-decoration: underline; font-family: 'Open Sans', sans-serif; font-size: 1.5vw">
								SÍGUENOS</p>
							<a href="https://www.instagram.com/futboldebarriosevilla/"
								style="text-decoration: none"><svg
									xmlns="http://www.w3.org/2000/svg" width="1.8vw" height="1.6vw"
									fill="currentColor" style="color: white; margin-right: 0.5vw"
									class="bi bi-instagram" viewBox="0 0 16 16">
									<path
										d="M8 0C5.829 0 5.556.01 4.703.048 3.85.088 3.269.222 2.76.42a3.9 3.9 0 0 0-1.417.923A3.9 3.9 0 0 0 .42 2.76C.222 3.268.087 3.85.048 4.7.01 5.555 0 5.827 0 8.001c0 2.172.01 2.444.048 3.297.04.852.174 1.433.372 1.942.205.526.478.972.923 1.417.444.445.89.719 1.416.923.51.198 1.09.333 1.942.372C5.555 15.99 5.827 16 8 16s2.444-.01 3.298-.048c.851-.04 1.434-.174 1.943-.372a3.9 3.9 0 0 0 1.416-.923c.445-.445.718-.891.923-1.417.197-.509.332-1.09.372-1.942C15.99 10.445 16 10.173 16 8s-.01-2.445-.048-3.299c-.04-.851-.175-1.433-.372-1.941a3.9 3.9 0 0 0-.923-1.417A3.9 3.9 0 0 0 13.24.42c-.51-.198-1.092-.333-1.943-.372C10.443.01 10.172 0 7.998 0zm-.717 1.442h.718c2.136 0 2.389.007 3.232.046.78.035 1.204.166 1.486.275.373.145.64.319.92.599s.453.546.598.92c.11.281.24.705.275 1.485.039.843.047 1.096.047 3.231s-.008 2.389-.047 3.232c-.035.78-.166 1.203-.275 1.485a2.5 2.5 0 0 1-.599.919c-.28.28-.546.453-.92.598-.28.11-.704.24-1.485.276-.843.038-1.096.047-3.232.047s-2.39-.009-3.233-.047c-.78-.036-1.203-.166-1.485-.276a2.5 2.5 0 0 1-.92-.598 2.5 2.5 0 0 1-.6-.92c-.109-.281-.24-.705-.275-1.485-.038-.843-.046-1.096-.046-3.233s.008-2.388.046-3.231c.036-.78.166-1.204.276-1.486.145-.373.319-.64.599-.92s.546-.453.92-.598c.282-.11.705-.24 1.485-.276.738-.034 1.024-.044 2.515-.045zm4.988 1.328a.96.96 0 1 0 0 1.92.96.96 0 0 0 0-1.92m-4.27 1.122a4.109 4.109 0 1 0 0 8.217 4.109 4.109 0 0 0 0-8.217m0 1.441a2.667 2.667 0 1 1 0 5.334 2.667 2.667 0 0 1 0-5.334" />
</svg> </a> <a href="https://x.com/FDB_Sevilla" style="text-decoration: none"><svg
									xmlns="http://www.w3.org/2000/svg" width="1.8vw" height="1.6vw"
									fill="currentColor"
									style="color: white; margin-right: 0.5vw; text-decoration: none"
									class="bi bi-twitter-x" viewBox="0 0 16 16">
  <path
										d="M12.6.75h2.454l-5.36 6.142L16 15.25h-4.937l-3.867-5.07-4.425 5.07H.316l5.733-6.57L0 .75h5.063l3.495 4.633L12.601.75Zm-.86 13.028h1.36L4.323 2.145H2.865z" />
</svg> </a> <a href="https://www.youtube.com/channel/UCfzHaUblCQl7lzp4CHXR9ug"
								style="text-decoration: none"><svg
									xmlns="http://www.w3.org/2000/svg" width="1.8vw" height="1.8vw"
									fill="currentColor" style="color: white; margin-right: 0.5vw;"
									class="bi bi-youtube" viewBox="0 0 16 16">
  <path
										d="M8.051 1.999h.089c.822.003 4.987.033 6.11.335a2.01 2.01 0 0 1 1.415 1.42c.101.38.172.883.22 1.402l.01.104.022.26.008.104c.065.914.073 1.77.074 1.957v.075c-.001.194-.01 1.108-.082 2.06l-.008.105-.009.104c-.05.572-.124 1.14-.235 1.558a2.01 2.01 0 0 1-1.415 1.42c-1.16.312-5.569.334-6.18.335h-.142c-.309 0-1.587-.006-2.927-.052l-.17-.006-.087-.004-.171-.007-.171-.007c-1.11-.049-2.167-.128-2.654-.26a2.01 2.01 0 0 1-1.415-1.419c-.111-.417-.185-.986-.235-1.558L.09 9.82l-.008-.104A31 31 0 0 1 0 7.68v-.123c.002-.215.01-.958.064-1.778l.007-.103.003-.052.008-.104.022-.26.01-.104c.048-.519.119-1.023.22-1.402a2.01 2.01 0 0 1 1.415-1.42c.487-.13 1.544-.21 2.654-.26l.17-.007.172-.006.086-.003.171-.007A100 100 0 0 1 7.858 2zM6.4 5.209v4.818l4.157-2.408z" />

							</a> <a href="https://www.tiktok.com/@fdb_sevilla"
								style="text-decoration: none"> <svg
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

				<div class=" col-12 d-block d-md-none d-sm-none">
					<div class="row">

						<div
							class=" col-4 d-block d-md-none d-sm-none g-2 pieDePagina p-2  ">
							<p
								style="text-decoration: underline; font-family: 'Open Sans', sans-serif; font-size: 2vw">ENLACES
								ÚTILES</p>
							<pre
								style="text-decoration: underline; font-family: 'Open Sans', sans-serif; font-size: 1.6vw">
<a href="${pageContext.request.contextPath}/pagoPremium" style="color: white">Hazte Premium</a>
<a href="${pageContext.request.contextPath}/club"
									style="color: white">Inicio</a>
<a href="${pageContext.request.contextPath}/club/plantilla"
									style="color: white">Plantilla</a>
<a href="${pageContext.request.contextPath}/marcadores"
									style="color: white">Marcadores</a>
<a href="${pageContext.request.contextPath}/club/eventos"
									style="color: white">Eventos</a></pre>
						</div>
						<div
							class="col-4 d-block d-md-none d-sm-none g-2 pieDePagina p-2 ">
							<p style="text-decoration: underline; font-size: 2vw">CONTACTO</p>
							<pre
								style="font-family: 'Open Sans', sans-serif; font-size: 1.6vw">futboldebarriosevilla@gmail.com
</pre>
						</div>
						<div
							class="col-4 d-block d-md-none d-sm-none g-2 pieDePagina p-2 ">
							<p
								style="text-decoration: underline; font-family: 'Open Sans', sans-serif; font-size: 2vw">
								SÍGUENOS</p>


							<a href="https://www.instagram.com/futboldebarriosevilla/"
								style="text-decoration: none"><svg
									xmlns="http://www.w3.org/2000/svg" width="2.2vw" height="2vw"
									fill="currentColor" style="color: white; margin-right: 0.5vw"
									class="bi bi-instagram" viewBox="0 0 16 16">
									<path
										d="M8 0C5.829 0 5.556.01 4.703.048 3.85.088 3.269.222 2.76.42a3.9 3.9 0 0 0-1.417.923A3.9 3.9 0 0 0 .42 2.76C.222 3.268.087 3.85.048 4.7.01 5.555 0 5.827 0 8.001c0 2.172.01 2.444.048 3.297.04.852.174 1.433.372 1.942.205.526.478.972.923 1.417.444.445.89.719 1.416.923.51.198 1.09.333 1.942.372C5.555 15.99 5.827 16 8 16s2.444-.01 3.298-.048c.851-.04 1.434-.174 1.943-.372a3.9 3.9 0 0 0 1.416-.923c.445-.445.718-.891.923-1.417.197-.509.332-1.09.372-1.942C15.99 10.445 16 10.173 16 8s-.01-2.445-.048-3.299c-.04-.851-.175-1.433-.372-1.941a3.9 3.9 0 0 0-.923-1.417A3.9 3.9 0 0 0 13.24.42c-.51-.198-1.092-.333-1.943-.372C10.443.01 10.172 0 7.998 0zm-.717 1.442h.718c2.136 0 2.389.007 3.232.046.78.035 1.204.166 1.486.275.373.145.64.319.92.599s.453.546.598.92c.11.281.24.705.275 1.485.039.843.047 1.096.047 3.231s-.008 2.389-.047 3.232c-.035.78-.166 1.203-.275 1.485a2.5 2.5 0 0 1-.599.919c-.28.28-.546.453-.92.598-.28.11-.704.24-1.485.276-.843.038-1.096.047-3.232.047s-2.39-.009-3.233-.047c-.78-.036-1.203-.166-1.485-.276a2.5 2.5 0 0 1-.92-.598 2.5 2.5 0 0 1-.6-.92c-.109-.281-.24-.705-.275-1.485-.038-.843-.046-1.096-.046-3.233s.008-2.388.046-3.231c.036-.78.166-1.204.276-1.486.145-.373.319-.64.599-.92s.546-.453.92-.598c.282-.11.705-.24 1.485-.276.738-.034 1.024-.044 2.515-.045zm4.988 1.328a.96.96 0 1 0 0 1.92.96.96 0 0 0 0-1.92m-4.27 1.122a4.109 4.109 0 1 0 0 8.217 4.109 4.109 0 0 0 0-8.217m0 1.441a2.667 2.667 0 1 1 0 5.334 2.667 2.667 0 0 1 0-5.334" />
</svg> </a> <a href="https://x.com/FDB_Sevilla" style="text-decoration: none"><svg
									xmlns="http://www.w3.org/2000/svg" width="2.2vw" height="2vw"
									fill="currentColor"
									style="color: white; margin-right: 0.5vw; text-decoration: none"
									class="bi bi-twitter-x" viewBox="0 0 16 16">
  <path
										d="M12.6.75h2.454l-5.36 6.142L16 15.25h-4.937l-3.867-5.07-4.425 5.07H.316l5.733-6.57L0 .75h5.063l3.495 4.633L12.601.75Zm-.86 13.028h1.36L4.323 2.145H2.865z" />
</svg> </a> <a href="https://www.youtube.com/channel/UCfzHaUblCQl7lzp4CHXR9ug"
								style="text-decoration: none"><svg
									xmlns="http://www.w3.org/2000/svg" width="2.2vw" height="2vw"
									fill="currentColor" style="color: white; margin-right: 0.5vw;"
									class="bi bi-youtube" viewBox="0 0 16 16">
  <path
										d="M8.051 1.999h.089c.822.003 4.987.033 6.11.335a2.01 2.01 0 0 1 1.415 1.42c.101.38.172.883.22 1.402l.01.104.022.26.008.104c.065.914.073 1.77.074 1.957v.075c-.001.194-.01 1.108-.082 2.06l-.008.105-.009.104c-.05.572-.124 1.14-.235 1.558a2.01 2.01 0 0 1-1.415 1.42c-1.16.312-5.569.334-6.18.335h-.142c-.309 0-1.587-.006-2.927-.052l-.17-.006-.087-.004-.171-.007-.171-.007c-1.11-.049-2.167-.128-2.654-.26a2.01 2.01 0 0 1-1.415-1.419c-.111-.417-.185-.986-.235-1.558L.09 9.82l-.008-.104A31 31 0 0 1 0 7.68v-.123c.002-.215.01-.958.064-1.778l.007-.103.003-.052.008-.104.022-.26.01-.104c.048-.519.119-1.023.22-1.402a2.01 2.01 0 0 1 1.415-1.42c.487-.13 1.544-.21 2.654-.26l.17-.007.172-.006.086-.003.171-.007A100 100 0 0 1 7.858 2zM6.4 5.209v4.818l4.157-2.408z" />

							</a> <a href="https://www.tiktok.com/@fdb_sevilla"
								style="text-decoration: none"> <svg
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
			</div>
		</div>
	</footer>



<script>
    // Guardar tipo y clubId en sessionStorage
    sessionStorage.setItem('tipoUsuario', '<%=tipoUsuario%>');
    sessionStorage.setItem('clubId', '<%=clubId%>');

   

    // Mostrar filtros
    function toggleFiltros(filtros, boton) {
        if (filtros.style.display === "none") {
            filtros.style.display = "flex";
            boton.textContent = "Ocultar Filtros";
        } else {
            filtros.style.display = "none";
            boton.textContent = "Mostrar Filtros";
        }
    }

    document.getElementById("mostrarFiltrosJugadores").addEventListener("click", function () {
        const filtros = document.getElementById("filtrosJugadores");
        toggleFiltros(filtros, this);
    });

    // Al cargar la página
    $(document).ready(function () {
        cargarJugadoresClub();
    });

    // Función para cargar jugadores
   function cargarJugadoresClub() {
    const clubId = sessionStorage.getItem("clubId");

    fetch("<%= request.getContextPath() %>/club/plantilla?clubId=" + clubId + "&tipo=jugadores", {
        credentials: 'include'
    })
    .then(response => response.json())
    .then(data => pintarJugadores(data)) // ✅ usamos directamente la lista
    .catch(err => console.error("Error al cargar jugadores:", err));
    }

// Función para eliminar miembro de club desde la tabla de jugadores
   function eliminarMiembroClub(idMiembroClub) {
       if (!confirm("¿Seguro que deseas eliminar a este miembro del club?")) return;

       if (!idMiembroClub || idMiembroClub <= 0) {
           alert("ID de miembro no válido.");
           return;
       }

       fetch("<%= request.getContextPath() %>/club/plantilla?idMiembroClub=" + idMiembroClub, { 
    	    method: "DELETE", 
    	    credentials: 'include' // mantener la sesión
    	})
       .then(res => {
           if (res.ok) {
               alert("Miembro eliminado correctamente.");
               cargarJugadoresClub(); // recargar tabla
           } else {
               alert("No se pudo eliminar al miembro.");
           }
       })
       .catch(err => console.error("Error al eliminar miembro del club:", err));
   }

   // Función para pintar la tabla de jugadores con estadísticas y idMiembroClub
   function pintarJugadores(jugadores) {
       var tbody = document.getElementById("tablaCuerpoJugador");
       tbody.innerHTML = "";

       if (!jugadores || jugadores.length === 0) {
           tbody.innerHTML = '<tr><td colspan="8">No hay jugadores disponibles</td></tr>';
           return;
       }

       var pos = 1;
       jugadores.forEach(function(j) {
           const idMiembro = j.idMiembroClub; // ahora seguro que existe

           var row = ''
               + '<tr id="fila-' + idMiembro + '" style="font-size: 1rem; text-align: center;">'
               +     '<td style="border:0.5px solid #8a210b; font-weight:bold;">' + pos++ + '</td>'
               +     '<td style="border:0.5px solid #8a210b;">' + j.nombreJugador + '</td>'
               +     '<td style="border:0.5px solid #8a210b;">' + j.aliasJugador + '</td>'
               +     '<td style="border:0.5px solid #8a210b;">' + j.partidosJugadosGlobal + '</td>'
               +     '<td style="border:0.5px solid #8a210b;">' + j.partidosGanadosGlobal + '</td>'
               +     '<td style="border:0.5px solid #8a210b;">' + (j.partidosEmpatadosGlobal || 0) + '</td>'
               +     '<td style="border:0.5px solid #8a210b;">' + j.partidosPerdidosGlobal + '</td>'
               + '<td style="border:0.5px solid #8a210b; text-align:center; vertical-align:middle; width:4vw;">'
               +     '<button class="btnEliminar" onclick="eliminarMiembroClub(' + idMiembro + ')" '
               +         'style="border:1px solid red; height:1.5rem; width:2rem; margin:0 auto; display:flex; justify-content:center; align-items:center;">'
               +         '<i class="bi bi-trash3-fill" style="color:#c33214; font-size:0.9rem;"></i>'
               +     '</button>'
               + '</td>'
               + '</tr>';

           tbody.innerHTML += row;
       });
   }



    // Función para filtrar tabla
    function filtrarTabla(idFiltro, columnaIndex) {
        const valor = document.getElementById(idFiltro).value.toLowerCase();
        const filas = document.querySelectorAll(".tablaDatosListaMarcadores tbody tr");

        filas.forEach(function(fila) {
            var texto = fila.getElementsByTagName("td")[columnaIndex].textContent.toLowerCase();
            fila.style.display = texto.includes(valor) ? "" : "none";
        });
    }

    // Eventos de filtros
    document.getElementById("buscarPosicionJugador").addEventListener("input", function () {
        filtrarTabla("buscarPosicionJugador", 0);
    });

    document.getElementById("buscarNombreJugador").addEventListener("input", function () {
        filtrarTabla("buscarNombreJugador", 1);
    });

    document.getElementById("buscarAliasJugador").addEventListener("input", function () {
        filtrarTabla("buscarAliasJugador", 2);
    });
    
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
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
		crossorigin="anonymous"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
		
</body>
</html>