package ru.job4j.cheessboard;

import ru.job4j.cheessboard.Cell;
import ru.job4j.cheessboard.Figure;
import ru.job4j.cheessboard.ImposibleMoveException;
import ru.job4j.cheessboard.OccupiedWayException;

class Slon extends Figure {


    public Slon(int x, int y) {
        super(x, y);
    }


    public boolean way(Cell source, Cell dest, Figure[][] figures) {
        if (Math.abs((dest.getX() - source.getX())) != Math.abs((dest.getY() - source.getY()))) {

            throw new ImposibleMoveException("ime");

        } else {
            if (dest.getY() < source.getY() && dest.getX() > source.getX()) {
                for (int i = 1; i <= Math.abs(dest.getX() - source.getX()); i++) {
                    if (figures[source.getX() + i][source.getY() - i] != null) {
                        throw new OccupiedWayException("owe");
                    }

                }
            } else {
                if (dest.getY() > source.getY() && dest.getX() < source.getX()) {
                    for (int i = 1; i <= Math.abs(dest.getX() - source.getX()); i++) {
                        if (figures[source.getX() - i][source.getY() + i] != null) {
                            throw new OccupiedWayException("owe");
                        }
                    }
                } else {
                    if (dest.getY() < source.getY() && dest.getX() < source.getX()) {
                        for (int i = 1; i <= Math.abs(dest.getX() - source.getX()); i++) {
                            if (figures[source.getX() - i][source.getY() - i] != null) {
                                throw new OccupiedWayException("owe");
                            }
                        }
                    } else {
                        for (int i = 1; i <= Math.abs(dest.getX() - source.getX()); i++) {
                            if (figures[source.getX() + i][source.getY() + i] != null) {
                                throw new OccupiedWayException("owe");
                            }
                        }
                    }
                }
            }
        }

        return true;
    }


    Figure clone(Cell dest) {
        return new Slon(dest.getX(), dest.getY());

    }
}