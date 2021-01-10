package com.OpenXcell.Filter;

import Utils.jwtUtil;
import com.OpenXcell.Authentication.Service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	@Autowired
	private MyUserDetailsService myUserDetailsService;

	@Autowired
	private jwtUtil jwtUtilInstance;

	@Override
	protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

		final String authorizationHeader = httpServletRequest.getHeader("Authorization");
		String userName=null;
		String jwt=null;

		//System.out.println("In filter authorizationHeader="+authorizationHeader);
		if(authorizationHeader!=null && authorizationHeader.startsWith("Bearer "))
		{
			jwt=authorizationHeader.substring(7);
			userName=jwtUtilInstance.extractUsername(jwt);
		}

		//System.out.println("In filter jwt="+jwt+" & username="+userName);
		if(userName!=null && SecurityContextHolder.getContext().getAuthentication()==null){

			UserDetails userDetails=this.myUserDetailsService.loadUserByUsername(userName);
			System.out.println("UserDetails "+userDetails.toString());
			if(jwtUtilInstance.validateToken(jwt,userDetails))
			{
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
				usernamePasswordAuthenticationToken
						.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));

				//System.out.println("username pass token="+usernamePasswordAuthenticationToken);
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

			}

		}
		filterChain.doFilter(httpServletRequest,httpServletResponse);
	}
}
