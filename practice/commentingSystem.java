import java.util.ArrayList;
import java.util.List;

// User class
class User {
    private static int counter = 0;
    private int id;
    private String name;

    public User(String name) {
        this.name = name;
        this.id = getUniqueID();
    }

    private static int getUniqueID() {
        return ++counter;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void addCommentToPost(Post post, Comment comment) {
        comment.setUserID(getId());
        comment.setPostId(post.getId());
        comment.setParentId(comment.getId());

        post.addComment(comment);
    }

    public void replyToComment(Post post, int parentId, Comment comment) {
        comment.setUserID(getId());
        comment.setPostId(post.getId());
        comment.setParentId(parentId);

        post.addNestedComment(comment, parentId);
    }

    public void editComment(Post post, int parentId, int commentId, String description) {
        for (Comment comment : post.getComments()) {
            if (comment.getId() == commentId) {
                if (comment.getUserID() != getId()) {
                    System.out.println("Unauthorized user");
                    return;
                }
                break;
            }
        }
        post.editComment(parentId, commentId, description);
    }

    public void deleteComment(Post post, int parentId, int commentId) {
        for (Comment comment : post.getComments()) {
            if (comment.getId() == commentId) {
                if (comment.getUserID() != getId()) {
                    System.out.println("Unauthorized user");
                    return;
                }
                break;
            }
        }
        post.deleteComment(parentId, commentId);
    }
}

// Post class
class Post {
    private static int counter = 0;
    private int id;
    private List<Comment> comments;

    public Post() {
        this.id = getUniqueID();
        this.comments = new ArrayList<>();
    }

    private static int getUniqueID() {
        return ++counter;
    }

    public int getId() {
        return id;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    public void addNestedComment(Comment comment, int parentid) {
        for (Comment c : comments) {
            if (c.getId() == parentid) {
                c.addComment(comment);
                return;
            }
        }
    }

    public void editComment(int parentId, int commentId, String description) {
        for (Comment comment : comments) {
            if (comment.getParentId() == parentId) {
                if (comment.getId() == commentId) {
                    comment.setDescription(description);
                } else {
                    for (Comment nestedComment : comment.getComments()) {
                        if (nestedComment.getId() == commentId) {
                            nestedComment.setDescription(description);
                            return;
                        }
                    }
                }
            }
        }
    }

    public void deleteComment(int parentId, int commentId) {
        for (Comment comment : comments) {
            if (comment.getParentId() == parentId) {
                if (comment.getId() == commentId) {
                    comments.remove(comment);
                    return;
                } else {
                    comment.deleteNestedComment(commentId);
                    return;
                }
            }
        }
    }
}

// Comment class
class Comment {
    private static int counter = 0;
    private int id;
    private int postId;
    private int userID;
    private String description;
    private List<Comment> comments;
    private int parentId;
    private boolean isParent;

    public Comment(String description) {
        this.description = description;
        this.id = getUniqueID();
        this.comments = new ArrayList<>();
        this.parentId = 0;
        this.isParent = false;
    }

    private static int getUniqueID() {
        return ++counter;
    }

    public int getId() {
        return id;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int value) {
        this.postId = value;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int value) {
        this.userID = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String value) {
        this.description = value;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int value) {
        this.parentId = value;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    public void deleteNestedComment(int commentId) {
        for (Comment comment : comments) {
            if (comment.getId() == commentId) {
                comments.remove(comment);
                return;
            }
        }
    }
}

// Main class
public class Main {
    public static void main(String[] args) {
        User u1 = new User("S");
        Comment c1 = new Comment("First Comment");
        Comment c2 = new Comment("Second Comment");
        Comment c21 = new Comment("Second Nested Comment");
        Comment c11 = new Comment("First Nested Comment");
        Comment c12 = new Comment("First Second Nested Comment");
        Comment c13 = new Comment("First Third Nested Comment");

        Post p = new Post();

        u1.addCommentToPost(p, c1);
        u1.addCommentToPost(p, c2);

        u1.replyToComment(p, c1.getId(), c11);
        u1.replyToComment(p, c1.getId(), c12);
        u1.replyToComment(p, c1.getId(), c13);
        u1.replyToComment(p, c2.getId(), c21);

        System.out.println("========================================================================");
        System.out.println("Printing Comments on the post");
        System.out.println("========================================================================");

        for (Comment com : p.getComments()) {
            System.out.println(com.getDescription());
            for (Comment nestedComment : com.getComments()) {
                System.out.println("\t" + nestedComment.getDescription());
            }
        }

        System.out.println("========================================================================");
        System.out.println("Editing Comments");
        System.out.println("========================================================================");
        u1.editComment(p, c1.getId(), c11.getId(), "Edited Comment");
        u1.editComment(p, c2.getId(), c12.getId(), "Edited Comment 2");

        System.out.println("========================================================================");
        System.out.println("Deleting Comments");
        System.out.println("========================================================================");
        u1.deleteComment(p, c2.getId(), c12.getId());

        System.out.println("========================================================================");
        System.out.println("Printing Comments on the post");
        System.out.println("========================================================================");

        for (Comment com : p.getComments()) {
            System.out.println(com.getDescription());
            for (Comment nestedComment : com.getComments()) {
                System.out.println("\t" + nestedComment.getDescription());
            }
        }
    }
}
