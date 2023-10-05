package by.gurinovich.bamper.services.post;

import by.gurinovich.bamper.exceptions.ResourceNotFoundException;
import by.gurinovich.bamper.models.postsEntities.Post;
import by.gurinovich.bamper.models.sparePartsEntities.SparePart;
import by.gurinovich.bamper.models.sparePartsEntities.SparePartName;
import by.gurinovich.bamper.models.user.User;
import by.gurinovich.bamper.repositories.posts.PostRepo;
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

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock private PostRepo postRepo;
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

    @BeforeEach
    void setUp(){
        sparePartName = SparePartName.builder()
                .id(1)
                .name("Engine")
                .build();
        sparePart = SparePart.builder()
                .id(1)
                .name(sparePartName)
                .number("123245")
                .build();
        post = Post.builder()
                .id(1)
                .title("Title")
                .sparePart(sparePart)
                .build();
        user = User.builder()
                .id(1)
                .username("roma")
                .email("roma@gmail.com")
                .phoneNumber("+375441234567")
                .enabled(true)
                .build();
        postCreation = PostCreation.builder()
                .sparePartId(1)
                .title("Title")
                .build();
    }

    @Test
    void getAll() {
        Mockito.when(postRepo.findAll()).thenReturn(List.of(post, new Post()));
        org.assertj.core.api.Assertions.assertThat(postService.getAll()).hasSize(2);
        Mockito.verify(postRepo).findAll();
    }

    @Test
    void shouldReturnExistingCarBrandById(){
        Mockito.when(postRepo.findById(Mockito.anyInt())).thenReturn(Optional.of(post));
        Assertions.assertEquals(post, postService.getById(Mockito.anyInt()));
        Mockito.verify(postRepo).findById(Mockito.anyInt());
    }


    @Test
    void throwExceptionWhenTryingToGetCarBrandByNotExistingId(){
        Mockito.when(postRepo.findById(Mockito.anyInt())).thenReturn(Optional.empty());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> postService.getById(Mockito.anyInt()));
        Mockito.verify(postRepo).findById(Mockito.anyInt());
    }

    @Test
    void getByUser() {
        Mockito.when(postRepo.findByUser(Mockito.any(User.class))).thenReturn(List.of(post, new Post()));
        org.assertj.core.api.Assertions.assertThat(postService.getByUser(user)).hasSize(2);
        Mockito.verify(postRepo).findByUser(Mockito.any(User.class));
    }

    @Test
    void save() {
        Mockito.when(sparePartService.getById(Mockito.anyInt())).thenReturn(sparePart);
        Mockito.when(userService.getAuthorizedUser()).thenReturn(user);
        Mockito.when(postRepo.save(Mockito.any(Post.class))).thenReturn(post);
        Assertions.assertEquals(post, postService.save(postCreation));
        Mockito.verify(sparePartService).getById(Mockito.anyInt());
        Mockito.verify(userService).getAuthorizedUser();
        Mockito.verify(postRepo).save(Mockito.any(Post.class));
    }

    @Test
    void uploadImage() {
    }

    @Test
    void deleteImage() {
    }

    @Test
    void deleteById() {
    }
}