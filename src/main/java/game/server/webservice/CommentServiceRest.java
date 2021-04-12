package game.server.webservice;

import game.entity.Comment;
import game.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class CommentServiceRest {

    @Autowired
    private CommentService commentService;

    @GetMapping("/{game}")
    public List<Comment> getComment(@PathVariable String game){
        return commentService.getComments(game);
    }

    @PostMapping
    public void addComment(@RequestBody Comment comment){
        commentService.addComment(comment);
    }
}
