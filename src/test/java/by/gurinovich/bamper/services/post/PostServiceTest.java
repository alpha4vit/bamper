package by.gurinovich.bamper.services.post;

import by.gurinovich.bamper.exceptions.ResourceNotFoundException;
import by.gurinovich.bamper.models.postsEntities.Image;
import by.gurinovich.bamper.models.postsEntities.Post;
import by.gurinovich.bamper.models.sparePartsEntities.SparePart;
import by.gurinovich.bamper.models.sparePartsEntities.SparePartName;
import by.gurinovich.bamper.models.user.User;
import by.gurinovich.bamper.repositories.posts.PostRepository;
import by.gurinovich.bamper.requests.PostCreation;
import by.gurinovich.bamper.services.sparePart.SparePartService;
import by.gurinovich.bamper.services.user.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock private PostRepository postRepository;
    @Mock private UserService userService;
    @Mock private ImageService imageService;
    @Mock private SparePartService sparePartService;

    @InjectMocks
    private PostService postService;

    private SparePartName sparePartName;
    private SparePart sparePart;
    private Post post;
    private User user;
    private PostCreation postCreation;
    private Image image;

    @BeforeEach
    void setUp(){
        sparePartName = SparePartName.builder()
                .id(1)
                .name("Engine")
                .build();
        sparePart = SparePart.builder()
                .id(1L)
                .name(sparePartName)
                .number("123245")
                .build();
        post = Post.builder()
                .id(1L)
                .title("Title")
                .sparePart(sparePart)
                .images(new ArrayList<>())
                .build();
        user = User.builder()
                .id(1L)
                .username("roma")
                .email("roma@gmail.com")
                .phoneNumber("+375441234567")
                .enabled(true)
                .build();
        postCreation = PostCreation.builder()
                .sparePartId(1L)
                .title("Title")
                .build();
        image = new Image(new MockMultipartFile("123", new byte[]{12}));
    }

    @Test
    void getAll() {
        Mockito.when(postRepository.findAll()).thenReturn(List.of(post, new Post()));
        org.assertj.core.api.Assertions.assertThat(postService.getAll()).hasSize(2);
        Mockito.verify(postRepository).findAll();
    }

    @Test
    void shouldReturnExistingCarBrandById(){
        Mockito.when(postRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(post));
        Assertions.assertEquals(post, postService.getById(Mockito.anyLong()));
        Mockito.verify(postRepository).findById(Mockito.anyLong());
    }


    @Test
    void throwExceptionWhenTryingToGetCarBrandByNotExistingId(){
        Mockito.when(postRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> postService.getById(Mockito.anyLong()));
        Mockito.verify(postRepository).findById(Mockito.anyLong());
    }

    @Test
    void getByUser() {
        Mockito.when(postRepository.findByUser(Mockito.any(User.class))).thenReturn(List.of(post, new Post()));
        org.assertj.core.api.Assertions.assertThat(postService.getByUser(user)).hasSize(2);
        Mockito.verify(postRepository).findByUser(Mockito.any(User.class));
    }

    @Test
    void save() {
        Mockito.when(sparePartService.getById(Mockito.anyLong())).thenReturn(sparePart);
        Mockito.when(userService.getAuthorizedUser()).thenReturn(user);
        Mockito.when(postRepository.save(Mockito.any(Post.class))).thenReturn(post);
        Assertions.assertEquals(post, postService.save(postCreation));
        Mockito.verify(sparePartService).getById(Mockito.anyLong());
        Mockito.verify(userService).getAuthorizedUser();
        Mockito.verify(postRepository).save(Mockito.any(Post.class));
    }

    @Test
    void uploadImage() {
        String imageName = "image name";
        Mockito.when(postRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(post));
        Mockito.when(imageService.upload(Mockito.any(Image.class))).thenReturn(imageName);
        postService.uploadImage(Mockito.anyLong(), image);
        Mockito.verify(postRepository).findById(Mockito.anyLong());
        Mockito.verify(imageService).upload(Mockito.any(Image.class));
        Mockito.verify(postRepository).save(Mockito.any(Post.class));
        //TODO not finished
    }

}