import java.util.Objects;

public class Node {
        private int x; // x-coordinate of node
        private int y; // y-coordinate of node

        public Node(int x, int y) {
            this.x = x;
            this.y = y;
        }

        // getters and setters for all fields
        public void setX(int x) {this.x = x;}
        public void setY(int y) {this.y = y;}
        public int getX() { return x; }
        public int getY() { return y; }
        
        @Override
        public boolean equals(Object obj) {
            boolean itIs = this == obj;
            if(!itIs || obj instanceof Node) {
                Node aux = (Node) obj;
                itIs = aux.x == this.x && aux.y == this.y;
            }
            return itIs;
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.x, this.y);
        }
    }