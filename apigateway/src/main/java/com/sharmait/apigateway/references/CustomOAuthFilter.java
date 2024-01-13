package com.sharmait.apigateway.references;

import com.google.gson.Gson;
import com.mettl.authenticator.constants.IErrorMessage;
import com.mettl.mpaasclientmodel.api.ApiErrorResponse;
import com.mettl.mpaasclientmodel.api.ApiErrorType;
import com.mettl.mpaasclientmodel.exceptions.MpsApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
@Slf4j
public class CustomOAuthFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response,
			final FilterChain filterChain) throws ServletException, IOException {

		String requestedUrl = request.getRequestURI();
		boolean isAuthenticated = false;
		HttpSession session = request.getSession(false);
		log.info("Session for requested url {} is {}", requestedUrl, session);
		if (session != null) {
			SecurityContextImpl securityContextImpl = (SecurityContextImpl) session
					.getAttribute("SPRING_SECURITY_CONTEXT");
			if (securityContextImpl != null) {
				isAuthenticated = true;
			}
		}
		log.debug("Is user authenticated ? : {}", isAuthenticated);

		if (isAuthenticated) {
			if (requestedUrl.equalsIgnoreCase("/admin-api/OAuthLogin")) {
				response.sendRedirect("/home");
				return;
			}
		} else {
			if (requestedUrl.equalsIgnoreCase("/admin-api/OAuthLogin")) {
				log.debug("Unauthenticated requests will redirect to okta");
			} else if(requestedUrl.equalsIgnoreCase("/admin-api/logout")){
				response.sendRedirect("/login");
				return;
			}else {
				log.debug("Unauthenticated request accessing secured apis will give error code");
				response.setStatus(HttpStatus.OK.value());
				ApiErrorResponse errorResponse = ApiErrorResponse
						.build(new MpsApiException(ApiErrorType.E022, IErrorMessage.UNAUTHENTICATED_REQUEST));
				String errorResponseAsJsonString = ((new Gson()).toJson(errorResponse)).toString();
				response.getWriter().write(errorResponseAsJsonString);
				return;
			}
		}


		filterChain.doFilter(request, response);
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
		String path = request.getServletPath();
		return !path.startsWith("/admin-api/");
	}
	
}
