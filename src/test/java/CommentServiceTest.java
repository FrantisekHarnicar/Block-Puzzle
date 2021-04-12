/*

import game.entity.Comment;
import game.service.CommentService;
import game.service.CommentServiceJDBC;
import org.junit.Test;


import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CommentServiceTest {

    private CommentService createService() {
        return new CommentServiceJDBC();
    }

    @Test
    public void testReset() {
        CommentService service = createService();
        service.reset();
        assertEquals(0, service.getComments("BlockPuzzle").size());
    }

    @Test
    public void testAddComment() {
        CommentService service = createService();
        service.reset();
        Date date = new Date();
        service.addComment(new Comment("BlockPuzzle", "Jaro", "ahoj kaaaaaaamo", date));

        List<Comment> comments = service.getComments("BlockPuzzle");

        assertEquals(1, comments.size());

        assertEquals("BlockPuzzle", comments.get(0).getGame());
        assertEquals("Jaro", comments.get(0).getPlayer());
        assertEquals("ahoj kaaaaaaamo", comments.get(0).getComment());
        assertEquals(date, comments.get(0).getCommentedOn());
    }

    @Test
    public void testComment() {
        CommentService service = createService();
        service.reset();
        Date date = new Date();
        service.addComment(new Comment("BlockPuzzle", "Jaro", "hala bala", date));
        service.addComment(new Comment("BlockPuzzle", "dusky", "dusko pisal", date));
        service.addComment(new Comment("BlockPuzzle", "majga", "aga bag buga", date));
        service.addComment(new Comment("BlockPuzzle", "zigizaga", "pisem pisem rozpravku", date));

        List<Comment> comment = service.getComments("BlockPuzzle");

        assertEquals(4, comment.size());

        assertEquals("hala bala", comment.get(0).getComment());
        assertEquals("dusky", comment.get(1).getPlayer());
        assertEquals("BlockPuzzle", comment.get(2).getGame());
        assertEquals("pisem pisem rozpravku", comment.get(3).getComment());
    }

}
*/