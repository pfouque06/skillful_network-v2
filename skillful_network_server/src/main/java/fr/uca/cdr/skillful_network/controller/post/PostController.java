package fr.uca.cdr.skillful_network.controller.post;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import fr.uca.cdr.skillful_network.entities.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import fr.uca.cdr.skillful_network.entities.post.Post;
import fr.uca.cdr.skillful_network.services.post.PostService;

@CrossOrigin("*")
@RestController
@RequestMapping("/posts")
public class PostController {

	@Autowired 
	private PostService postService;
	
	@GetMapping(value="")
	public List<Post> getAll(){
		return this.postService.getAll();
	}
	
	@PostMapping(value="")
	public Post create(@Valid @RequestBody String body) {
		return this.postService.createPost(body);
	}

	@PostMapping(value="/{userId}")
	public Post create(
			@PathVariable(value = "userId") Long userId,
			@Valid @RequestBody String body) {
		return this.postService.createPost(userId, body);
	}

 	@PutMapping(value = "/{id}")
	@Transactional
	public ResponseEntity<Post> update(
			@PathVariable(value = "id") Long id,
			@Valid @RequestBody String body) {
		return new ResponseEntity<>(
				this.postService.update(
						id,
						body,
						new Date() // TODO for now we replace the creation date to mark the update but we could keep track of the edition and versions
				), HttpStatus.OK);
	}

	@PutMapping(value = "/{userId}/{id}")
	@Transactional
	public ResponseEntity<Post> update(
			@PathVariable(value = "userId") Long userId,
			@PathVariable(value = "id") Long id,
			@Valid @RequestBody String body) {
		return new ResponseEntity<>(
				this.postService.update(
						userId,
						id,
						body,
						new Date() // TODO for now we replace the creation date to mark the update but we could keep track of the edition and versions
				), HttpStatus.OK);
	}

	@DeleteMapping(value = "/{id}")
    @Transactional
    public void delete(@PathVariable(value = "id") Long id) {
        postService.deletePostById(id);
    }

	@DeleteMapping(value = "/{userId}/{id}")
    @Transactional
    public void delete(
			@PathVariable(value = "userId") Long userId,
			@PathVariable(value = "id") Long id) {
        postService.deletePostById(userId, id);
    }
	
	@GetMapping(value="/{userId}")
	public ResponseEntity<List<Post>> getPostsByUserId(@PathVariable(value= "userId") long userId){
		List<Post> posts = this.postService.getByUserId(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aucune publication n'est publiée."));
		return new ResponseEntity<>( posts, HttpStatus.OK);
	}
	
	@GetMapping(value="/{id}/commentNumber")
	public int commentNumberByPostId(@PathVariable(value="id") long id) {
		return this.postService.getPostById(id).get().getCommentsNumber();
	}
	@PreAuthorize("hasAnyRole('ENTREPRISE','ORGANISME','USER')")
	@GetMapping(value="/{id}/user")
	public ResponseEntity<User> getUserByPostId(@PathVariable(value="id") long postId) {
		User ownerPost = this.postService.getUserByPostId(postId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, " Post not found with id : " + postId));
		return new ResponseEntity<User>(ownerPost, HttpStatus.OK);
	}
	
}
