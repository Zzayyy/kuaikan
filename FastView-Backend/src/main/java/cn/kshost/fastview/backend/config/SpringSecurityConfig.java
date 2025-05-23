package cn.kshost.fastview.backend.config;

import cn.kshost.fastview.backend.security.LoginAuthenticationEntryPoint;
import cn.kshost.fastview.backend.security.LogoutAuthentication;
import cn.kshost.fastview.backend.security.TokenParseFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@EnableMethodSecurity
@Configuration
public class SpringSecurityConfig {
    @Autowired
    private TokenParseFilter tokenParseFilter;

    @Autowired
    private LogoutAuthentication logoutAuthentication;

    @Autowired
    LoginAuthenticationEntryPoint loginAuthenticationEntryPoint;


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)  //关闭csrf防护
                .sessionManagement(t -> t.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //设置无状态会话，不使用session
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/user/login").permitAll()//放行指定路径
                        .requestMatchers("/doc.html").permitAll() // 放行 Swagger UI 的文档页面
                        .requestMatchers("/swagger-ui/**").permitAll() // 放行 Swagger UI 的静态资源
                        .requestMatchers("/webjars/**").permitAll() // 放行 Swagger 所需的 WebJars 资源
                        .requestMatchers("/v3/api-docs").permitAll() // 放行 OpenAPI 文档路径
                        .requestMatchers("/v3/api-docs/**").permitAll() // 放行 OpenAPI 文档的子路径
                        .requestMatchers("/swagger-resources/**").permitAll()
                        .requestMatchers("//knife4j/**").permitAll()





                        .anyRequest().authenticated()); //其它路径都需要认证
        //自定义token过滤器注册
        http.addFilterBefore(tokenParseFilter, UsernamePasswordAuthenticationFilter.class);
        //注册匿名访问私有资源的处理器
        http.exceptionHandling(handling -> handling.authenticationEntryPoint(loginAuthenticationEntryPoint));
        //注册自定义注销处理器
        http.logout(t -> t.logoutSuccessHandler(logoutAuthentication));
        return http.build();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
