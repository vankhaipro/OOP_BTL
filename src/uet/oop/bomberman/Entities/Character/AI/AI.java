package uet.oop.bomberman.Entities.Character.AI;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Entities.Bomb.Bomb;
import uet.oop.bomberman.Entities.Character.Bomber;
import uet.oop.bomberman.Entities.Character.Enemy.Enemy;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/* 0 : up
1 : right
2: down
3: left
*/
public class AI {
    int[] deltaX = {0, 1, 0, -1};
    int[] deltaY = {-1, 0, 1, 0};
    double bomberX;
    double bomberY;
    double tempX ;
    double tempY ;
    double dX ;
    double dY ;
    double distance ;
    private Random random = new Random();
    //thêm thông tin tọa độ enemy
    public void createInfo(Enemy enemy) {
        bomberX = Board.getPlayer().getX();
        bomberY = Board.getPlayer().getY();
        tempX = (double) Math.round(enemy.getX() * 1000) / 1000;
        tempY = (double) Math.round(enemy.getY() * 1000) / 1000;
        dX = bomberX - enemy.getX();
        dY = bomberY - enemy.getY();
        distance = Math.sqrt(dX * dX + dY * dY);
    }
    //hướng di chuyển tôối ưu nhất
    public int bestDirection(Enemy enemy, ArrayList<Integer> possibleDirections, int size) {
        double bomberX = Board.getPlayer().getX();
        double bomberY = Board.getPlayer().getY();


        double[] distance = new double[4];
        double min = 1000;
        int minDirection = 0;
        for (int i = 0; i < size; i++) {
            double tempX = (double) Math.round(enemy.getX() * 1000) / 1000 + deltaX[possibleDirections.get(i)];
            double tempY = (double) Math.round(enemy.getY() * 1000) / 1000 + deltaY[possibleDirections.get(i)];
            double dX = bomberX - tempX;
            double dY = bomberY - tempY;
            distance[i] = Math.sqrt(dX * dX + dY * dY);
        }
        for (int i = 0; i < size; i++) {
            if (distance[i] < min) {
                min = distance[i];
                minDirection = i;
            }
        }
        return minDirection;
    }
    //trả về hướng di chuyển ngẫu nghiên nếu mà tọa độ nguyên  nếu không trả về vị trí hiện tại
    public int chooseDirectionRandom(Enemy enemy, int currentDirection) {
        double tempX = (double) Math.round(enemy.getX() * 1000) / 1000;
        double tempY = (double) Math.round(enemy.getY() * 1000) / 1000;
        if (tempX == (int) tempX && tempY == (int) tempY) {
            Random rd = new Random(System.currentTimeMillis());
            return Math.abs(rd.nextInt()) % 4;
        } else {
            return currentDirection;
        }
    }
    //khi enemy gần bomber thì tăng speed lên
    public int chooseDirectionMedium(Enemy enemy, int currentDirection) {
        createInfo(enemy);

        if (distance > 10) {
            enemy.setSpeed(Board.speedOfEnemy);
        }

        if (distance < 3) {
            enemy.setSpeed(Board.speedOfEnemy * 2);
        }
        if (distance < 6) {
            if (tempX == (int) tempX && tempY == (int) tempY) {
                ArrayList<Integer> possibleDirections = new ArrayList<>();
                if (dX > 0) {
                    possibleDirections.add(1);
                } else possibleDirections.add(3);

                if (dY > 0) {
                    possibleDirections.add(2);
                } else possibleDirections.add(0);

                return possibleDirections.get(Math.abs(random.nextInt() % 2));
            }
            return currentDirection;
        } else return chooseDirectionRandom(enemy, currentDirection);
    }
    //hướng di chuyển tối ưu để tránh bom
    public int chooseDirectionMedium2(Bomber bomber, Enemy enemy, int currentDirection) {
        createInfo(enemy);

        double distance = Math.sqrt(dX * dX + dY * dY);

        if (distance < Math.sqrt(50)) {
            if (tempX == (int) tempX && tempY == (int) tempY) {
                ArrayList<Integer> possibleDirections = new ArrayList<>();
                if (dX > 0) {
                    if (checkBomAvoidance(bomber, enemy, 1))
                        possibleDirections.add(1);
                } else {
                    if (checkBomAvoidance(bomber, enemy, 3))
                        possibleDirections.add(3);
                }

                if (dY > 0) {
                    if (checkBomAvoidance(bomber, enemy, 2))
                        possibleDirections.add(2);
                } else {
                    if (checkBomAvoidance(bomber, enemy, 0))
                        possibleDirections.add(0);
                }

                switch (possibleDirections.size()) {
                    case 0:
                        int direction = -1;
                        for (int i = 0; i < 4; i++) {
                            if (checkBomAvoidance(bomber, enemy, i)) {
                                direction = i;
                            }
                        }
                        if (direction != -1) return direction;
                        else {
                            return chooseDirectionRandom(enemy, currentDirection);
                        }
                    case 1:
                        return possibleDirections.get(0);
                    case 2:
                        return possibleDirections.get(Math.abs(random.nextInt() % 2));
                }
            }
            return currentDirection;
        } else return chooseDirectionRandom(enemy, currentDirection);
    }
    //hướng di chuyển tối ưu tránh tường và bom
    public int chooseDirectionGoThroughBrick(Bomber bomber, Enemy enemy, int currentDirection) {
        double tempX = (double) Math.round(enemy.getX() * 1000) / 1000;
        double tempY = (double) Math.round(enemy.getY() * 1000) / 1000;
        if (tempX == (int) tempX && tempY == (int) tempY) {
            ArrayList<Integer> possibleDirections = new ArrayList<>();

            for (int i = 0; i < 4; i++) {
                if (checkCanMoveThroughBrick(enemy, i) && checkBomAvoidance(bomber, enemy, i))

                    possibleDirections.add(i);
            }

            int size = possibleDirections.size();

            if (size == 0) return -1;

            else {

                int i = bestDirection(enemy, possibleDirections, size);

                return possibleDirections.get(i);
            }
        } else {
            return currentDirection;
        }
    }
    //kiểm tra xem hướng đi tránh đc bom k
    public boolean checkBomAvoidance(Bomber bomber, Enemy enemy, int direction) {
        List<Bomb> bombs = bomber.getBombs();
        for (Bomb bomb : bombs) {
            if (!avoidBomb(enemy, bomb.getX(), bomb.getY(), direction))
                return false;
        }
        return true;
    }
    //ktra tọa độ gần bom không
    public boolean avoidBomb(Enemy enemy, double xBomb, double yBomb, int direction) {
        double X = (double) Math.round(enemy.getX() * 1000) / 1000;
        double Y = (double) Math.round(enemy.getY() * 1000) / 1000;

        int xEnemy = (int) X;
        int yEnemy = (int) Y;

        xEnemy += deltaX[direction];
        yEnemy += deltaY[direction];

        int dX = xEnemy - (int) xBomb;
        int dY = yEnemy - (int) yBomb;

        int distanceSquare = dX * dX + dY * dY;

        if (distanceSquare < 3 * Board.bombRadius) {
            return false;
        }
        {
            return true;
        }
    }
     //kiểm tra xem enemy di chuyển qua một ô gạch khong
    public boolean checkCanMoveThroughBrick(Enemy enemy, int direction) {
        double tempX = (double) Math.round(enemy.getX() * 1000) / 1000;
        double tempY = (double) Math.round(enemy.getY() * 1000) / 1000;

        tempX += deltaX[direction];
        tempY += deltaY[direction];
        return Board.map[(int) tempY][(int) tempX] != '#';
    }

}