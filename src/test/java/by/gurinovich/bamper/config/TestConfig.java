package by.gurinovich.bamper.config;

import by.gurinovich.bamper.props.JWTProperties;
import by.gurinovich.bamper.props.MinioProperties;
import by.gurinovich.bamper.repositories.car.CarBrandRepo;
import by.gurinovich.bamper.repositories.car.CarModelGenerationRepo;
import by.gurinovich.bamper.repositories.car.CarModelRepo;
import by.gurinovich.bamper.repositories.posts.PostRepo;
import by.gurinovich.bamper.repositories.sparePart.SparePartNameRepo;
import by.gurinovich.bamper.repositories.sparePart.SparePartRepo;
import by.gurinovich.bamper.repositories.user.UserRepo;
import by.gurinovich.bamper.security.JWTTokenProvider;
import by.gurinovich.bamper.security.JWTUserDetailsService;
import by.gurinovich.bamper.services.car.CarBrandService;
import by.gurinovich.bamper.services.car.CarModelGenerationService;
import by.gurinovich.bamper.services.car.CarModelService;
import by.gurinovich.bamper.services.post.ImageService;
import by.gurinovich.bamper.services.post.PostService;
import by.gurinovich.bamper.services.sparePart.SparePartNameService;
import by.gurinovich.bamper.services.sparePart.SparePartService;
import by.gurinovich.bamper.services.user.AuthService;
import by.gurinovich.bamper.services.user.UserService;
import io.minio.MinioClient;
import lombok.RequiredArgsConstructor;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@TestConfiguration
@RequiredArgsConstructor
public class TestConfig {

    private final AuthenticationManager authenticationManager;
    private final CarBrandRepo carBrandRepo;
    private final CarModelRepo carModelRepo;
    private final CarModelGenerationRepo carModelGenerationRepo;
    private final PostRepo postRepo;
    private final SparePartRepo sparePartRepo;
    private final SparePartNameRepo sparePartNameRepo;
    private final UserRepo userRepo;

    @Bean
    @Primary
    public BCryptPasswordEncoder testPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JWTProperties testJWTProperties(){
        JWTProperties jwtProperties = new JWTProperties();
        jwtProperties.setSecret("ZHNhZ3IyamFzZDN3cWVnZmUzMWc2dWpn");
        return jwtProperties;
    }

    @Bean
    @Primary
    public UserDetailsService userDetailsService(){
        return new JWTUserDetailsService(userService());
    }

    @Bean
    public MinioClient minioClient(){
        return Mockito.mock(MinioClient.class);
    }

    @Bean
    @Primary
    public MinioProperties minioProperties(){
        MinioProperties minioProperties = new MinioProperties();
        minioProperties.setBucket("images");
        return minioProperties;
    }

    @Bean
    @Primary
    public ImageService imageService(){
        return new ImageService(minioClient(), minioProperties());
    }

    @Bean
    public JWTTokenProvider jwtTokenProvider(){
        return new JWTTokenProvider(testJWTProperties(),
                userDetailsService(),
                userService());
    }

    @Bean
    @Primary
    public SparePartService sparePartService(){
        return new SparePartService(sparePartRepo, sparePartNameRepo);
    }

    @Bean
    @Primary
    public UserService userService(){
        return new UserService(userRepo, testPasswordEncoder());
    }

    @Bean
    @Primary
    public PostService postService(){
        return new PostService(postRepo, userService(), imageService(), sparePartService());
    }

    @Bean
    @Primary
    public CarBrandService carBrandService(){
        return new CarBrandService(carBrandRepo);
    }

    @Bean
    @Primary
    public CarModelService carModelService(){
        return new CarModelService(carModelRepo);
    }

    @Bean
    @Primary
    public CarModelGenerationService carModelGenerationService(){
        return new CarModelGenerationService(carModelGenerationRepo);
    }

    @Bean
    @Primary
    public SparePartNameService sparePartNameService(){
        return new SparePartNameService(sparePartNameRepo);
    }

    @Bean
    @Primary
    public AuthService authService(){
        return new AuthService(authenticationManager, userService(), jwtTokenProvider());
    }


}
