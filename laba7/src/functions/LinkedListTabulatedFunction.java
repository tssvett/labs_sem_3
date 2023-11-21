package functions;

import java.io.Serializable;

public class LinkedListTabulatedFunction implements TabulatedFunction, Serializable {
    private final FunctionNode head;
    private int length;

    private class FunctionNode {
        private FunctionPoint value;
        private FunctionNode next;
        private FunctionNode prev;
        public FunctionNode() {
            /*
             * Конструктор по умолчанию
             */
            this.value = null;
            this.next = this;
            this.prev = this;
        }
        public FunctionNode(FunctionPoint value){
            /*
             * Конструктор с параметром в виде значения узла, но без ссылок на предыдущий и последующий узлы
             */
            this.value = value;
            this.next = this;
            this.prev = this;
        }
        public FunctionNode(FunctionPoint value, FunctionNode next){
            /*
             * Конструктор с параметрами в виде значения узла и ссылки на следующий узел
             */
            this.value = value;
            this.next = next;
            this.prev = this;
        }
        public FunctionNode(FunctionNode functionNode){
            /*
             * Конструктор копирования
             */
            this.value = functionNode.value;
            this.next = functionNode.next;
            this.prev = functionNode.prev;
        }
        public FunctionNode(FunctionPoint value, FunctionNode next, FunctionNode prev){
            /*
             * Конструктор с параметрами в виде значения узла, ссылки на следующий и предыдущий узлы
             */
            this.value = value;
            this.next = next;
            this.prev = prev;
        }

        public FunctionPoint getValue(){
            /*
             * Возвращает значение узла
             */
            return this.value;
        }
        public void setValue(FunctionPoint value){
            /*
             * Устанавливает значение узла
             */
            this.value = value;
        }
        public FunctionNode getNext(){
            /*
             * Возвращает ссылку на следующий узел для сохранения инкапсуляции
             */
            return this.next;
        }
        public FunctionNode getPrev(){
            /*
             * Возвращает ссылку на предыдущий узел для сохранения инкапсуляции
             */
            return this.prev;
        }
        public void setNext(FunctionNode next){
            /*
             * Устанавливает ссылку на следующий узел
             */
            this.next = next;
        }
        public void setPrev(FunctionNode prev){
            /*
             * Устанавливает ссылку на предыдущий узел
             */
            this.prev = prev;
        }
    }

    public FunctionNode getNodeByIndex(int index) throws FunctionPointIndexOutOfBoundsException{
        /*
         * Возвращает узел по индексу
         */
        if (index > this.length - 1 || index < 0) {
            throw new FunctionPointIndexOutOfBoundsException();
        }
        FunctionNode ptr = this.head.getNext();
        for(int i = 0; i < index; i++){
            ptr = ptr.getNext();
        }
        return ptr;
    }

    public FunctionNode addNodeToTail(){
        /*
         * Добавляет элемент в конец списка и возвращает ссылку на него
         */
        if (this.length == 0) {
            return this.addNodeByIndex(0, new FunctionPoint());
        }
        FunctionNode node = new FunctionNode(new FunctionPoint());
        node.setPrev(this.head.getNext().getPrev());
        this.head.getNext().getPrev().setNext(node);
        this.head.getNext().setPrev(node);
        node.setNext(this.head.getNext());
        ++length;
        return node;
    }

    public FunctionNode addNodeByIndex(int index, FunctionPoint point) throws FunctionPointIndexOutOfBoundsException{
        /*
         * Добавляет элемент в список по индексу и возвращает ссылку на элемент
         */
        if (index > this.length - 1 || index < 0) {
            throw new FunctionPointIndexOutOfBoundsException();
        }
        FunctionNode node = new FunctionNode(point);
        if (index == 0 && this.length == 0){
            this.head.setNext(node);
            ++length;
            return node;
        }
        else {
            FunctionNode ptr = this.head.getNext();
            for (int i = 0; i < index; i++) {
                ptr = ptr.getNext();
            }
            node.setPrev(ptr.getPrev());
            node.setNext(ptr);
            ptr.getPrev().setNext(node);
            ptr.setPrev(node);
            ++length;
            if (node.getValue().getX() < head.getNext().getValue().getX()){
                this.head.setNext(node);
            }
            return node;
        }
    }

    public FunctionNode deleteNodeByIndex(int index) throws FunctionPointIndexOutOfBoundsException, IllegalStateException{
        /*
         * Удаляет элемент по индексу и возвращает ссылку на элемент
         */
        if (index > this.length - 1 || index < 0) {
            throw new FunctionPointIndexOutOfBoundsException();
        } else if (this.length < 3) {
            throw new IllegalStateException("Недостаточно точек для удаления, должно быть минимум 3");
        }
        FunctionNode ptr = this.head.getNext();
        for (int i = 0; i <= index; i++) {
            ptr = ptr.getNext();
        }
        ptr.getPrev().setNext(ptr.getNext());
        ptr.getNext().setPrev(ptr.getPrev());
        --length;
        return ptr;
    }


    public LinkedListTabulatedFunction(double leftX, double rightX, int pointsCount){
        //Конструктор, создающий табличную функцию с указанным количеством точек между границами домена leftX и rightX.
        if (leftX >= rightX || pointsCount < 2){
            throw new IllegalArgumentException("[!] Ошибка! Левая граница должна быть меньше правой. Точек должно быть больше или равно двух.");
        }
        this.head = new FunctionNode();
        FunctionNode functionNode = new FunctionNode(new FunctionPoint(leftX, 0));
        this.head.setNext(functionNode);
        FunctionNode ptr;
        double interval = (rightX - leftX) / (pointsCount - 1);
        for (int i = 1; i < pointsCount; i++){
            ptr = new FunctionNode(new FunctionPoint(leftX + i * interval, 0));
            functionNode.setNext(ptr);
            ptr.setPrev(functionNode);
            functionNode = ptr;
        }
        functionNode.setNext(this.head.getNext());
        this.head.getNext().setPrev(functionNode);
        this.length = pointsCount;
    }

    public LinkedListTabulatedFunction(double leftX, double rightX, double[] values){
        //Конструктор, создающий табличную функцию с точками в указанных координатах X и соответствующих значениях Y.
        if (leftX >= rightX || values.length < 2){
            throw new IllegalArgumentException("[!] Ошибка! Левая граница должна быть меньше правой.  Точек должно быть больше или равно двух.");
        }
        this.head = new FunctionNode();
        FunctionNode functionNode = new FunctionNode(new FunctionPoint(leftX, values[0]));
        FunctionNode ptr;
        this.head.setNext(functionNode);
        double interval = (rightX - leftX) / (values.length - 1);
        for(int i = 1; i < values.length; i++){
            ptr = new FunctionNode(new FunctionPoint(leftX + i * interval, values[i]));
            functionNode.setNext(ptr);
            ptr.setPrev(functionNode);
            functionNode = ptr;
        }
        this.head.getNext().setPrev(functionNode);
        functionNode.setNext(this.head.getNext());
        this.length = values.length;
    }

    public LinkedListTabulatedFunction(FunctionPoint[] pointsList){
        if (pointsList.length < 2){
            throw new IllegalArgumentException("[!] Ошибка! Точек должно быть больше или равно двух.");
        }
        if (!isSortedByX(pointsList)){
            throw new IllegalArgumentException("[!] Ошибка! Точки должны быть отсортированы по X.");
        }
        this.head = new FunctionNode();
        FunctionNode functionNode = new FunctionNode(pointsList[0]);
        FunctionNode ptr;
        this.head.setNext(functionNode);
        for(int i = 1; i < pointsList.length; i++){
            ptr = new FunctionNode(pointsList[i]);
            functionNode.setNext(ptr);
            ptr.setPrev(functionNode);
            functionNode = ptr;
        }
        this.head.getNext().setPrev(functionNode);
        functionNode.setNext(this.head.getNext());
        this.length = pointsList.length;
    }

    public LinkedListTabulatedFunction(){
        /*
         * Конструктор по умолчанию
         */
        this.head = new FunctionNode();
        this.length = 0;
    }

    private boolean isSortedByX(FunctionPoint[] pointsList){
        for (int i = 0; i < pointsList.length - 1; i++){
            if (pointsList[i].getX() > pointsList[i + 1].getX()){
                return false;
            }
        }
        return true;
    }

    public double getLeftDomainBorder(){
        //Возвращает координату X левой границы.
        return this.head.getNext().getValue().getX();
    }

    public double getRightDomainBorder(){
        //Возвращает координату Х правой границы.
        return this.head.getNext().getPrev().getValue().getX();
    }


    private boolean isInsideBorder(double x){
        //Проверка на принадлежности точки по Х  в заданном промежутке
        return (x >= this.getLeftDomainBorder() && x <= this.getRightDomainBorder());
    }

    private double linearInterpolation(double x, int pointNumber){
        //Линейная интерполяция для определения значения точек, лежащих между другими точками
        /*
             y2 - y1 = k(x2 - x1) + b
             k = (y2 - y1) / (x2 - x1)
             b = y1 - k
        */
        double k = (this.getNodeByIndex(pointNumber).getValue().getY() - this.getNodeByIndex(pointNumber+1).getValue().getY()) / (this.getNodeByIndex(pointNumber).getValue().getX() - this.getNodeByIndex(pointNumber+1).getValue().getX());
        double b = this.getNodeByIndex(pointNumber).getValue().getY() - k * this.getNodeByIndex(pointNumber).getValue().getX();
        return k * x + b;
    }

    private int[] binarySearch(double x){
        //Алгоритм бинарного поиска. Ищет индекс точки, в которую будем ее вставлять
        //Возвращаем значение в зависимости от метки 0 или 1.
        //Если метка 1, то элемент есть и возвращаем его индекс.
        //Если метка 0, то элемента нет и возвращаем его левого соседа.
        int[] results = new int[2];
        int left = 0;
        int right = getPointsCount() - 1;
        while (left <= right){
            int middle = (left + right) / 2;
            double guess = this.getNodeByIndex(middle).getValue().getX();
            if (guess == x){
                results[0] = middle;
                results[1] = 1;
                return results;
            }
            if (guess < x){
                left = middle + 1;
            }
            if (guess > x){
                right = middle - 1;
            }
        }
        results[0] = left;
        results[1] = 0;
        return results;
    }

    public double getFunctionValue(double x) {
        //Вычисляет и возвращает значение функции по заданной координате X с использованием линейной интерполяции.
        if (!isInsideBorder(x)) {
            return Double.NaN;
        }
        int[] result = binarySearch(x);
        if (result[1] == 1){
            return this.getNodeByIndex(result[0]).getValue().getY();
        }
        else {
            return linearInterpolation(x, result[0]);
        }
    }


    public int getPointsCount(){
        //Возвращает число точек в функции
        return this.length;
    }

    public FunctionPoint getPoint(int i){
        //Возвращает точку по индексу
        if (!isCorrectIndex(i)){
            throw new FunctionPointIndexOutOfBoundsException(i);
        }
        return this.getNodeByIndex(i).getValue();
    }

    private boolean isInCorrectSortedPlace(FunctionPoint point, int index){
        //Проверка на недопустимую точку
        return (point.getX() < this.getNodeByIndex(index-1).getValue().getX() || point.getX() > this.getNodeByIndex(index + 1).getValue().getX());
    }

    private boolean isCorrectIndex(int index){
        //Проверка на корректность индекса
        return (index >= 0 && index < this.length);
    }
    public void setPoint(int index, FunctionPoint point) throws FunctionPointIndexOutOfBoundsException,
            InappropriateFunctionPointException {
        //Устанавливает точку по индексу
        if (!isCorrectIndex(index)){
            throw new FunctionPointIndexOutOfBoundsException(index);
        }
        if (isInCorrectSortedPlace(point, index)){
            throw new InappropriateFunctionPointException(index);
        }
        this.getNodeByIndex(index).setValue(point);
    }

    public double getPointX(int index){
        //Возвращает координату Х точки по индексу
        if (!isCorrectIndex(index)){
            throw new FunctionPointIndexOutOfBoundsException(index);
        }
        return this.getNodeByIndex(index).getValue().getX();
    }

    public void setPointX(int index, double x) throws FunctionPointIndexOutOfBoundsException,
            InappropriateFunctionPointException {
        // Устанавливает координату Х точки по индексу
        if (!isCorrectIndex(index)){
            throw new FunctionPointIndexOutOfBoundsException(index);
        }
        if (isInCorrectSortedPlace(this.getNodeByIndex(index).getValue(), index)){
            throw new InappropriateFunctionPointException(index);
        }
        this.getNodeByIndex(index).getValue().setX(x);
    }

    public double getPointY(int index){
        //Возвращает координату Y точки по индексу
        if (!isCorrectIndex(index)){
            throw new FunctionPointIndexOutOfBoundsException(index);
        }
        return this.getNodeByIndex(index).getValue().getY();
    }

    public void setPointY(int index, double y){
        //Устанавливает координату Y точки по индексу
        if (!isCorrectIndex(index)){
            throw new FunctionPointIndexOutOfBoundsException(index);
        }
        this.getNodeByIndex(index).getValue().setY(y);
    }

    public void deletePoint(int index){
        //Удаляет точку по индексу. Так же изменяет размер массива при необходимости.
        if (!isCorrectIndex(index)){
            throw new FunctionPointIndexOutOfBoundsException(index);
        }
        if (this.length < 3){
            throw new IllegalStateException("[!] Ошибка! Точек и так меньше трех.... Куда меньше??");
        }
        if (index >= 0 && index < getPointsCount()) {
            this.deleteNodeByIndex(index);
        }
    }


    private int findIndex(double x){
        //Алгоритм бинарного поиска. Ищет индекс точки, в которую будем ее вставлять
        int left = 0;
        int right = getPointsCount() - 1;
        while (left <= right){
            int middle = (left + right) / 2;
            double guess = this.getNodeByIndex(middle).getValue().getX();
            if (guess == x){
                return -1;
            }
            if (guess < x){
                left = middle + 1;
            }
            if (guess > x){
                right = middle - 1;
            }
        }
        return left;
    }

    public void addPoint(FunctionPoint point) throws InappropriateFunctionPointException {
        //Добавление точки с сохранением сортировки по х
        int indexToInsert = findIndex(point.getX());
        if (indexToInsert == -1){
            throw new InappropriateFunctionPointException("Ошибка! Попытка добавить точку с координатой Х, которая уже существует.");
        }
        else {
            addNodeByIndex(indexToInsert, point);
        }
    }

    @Override
    public String toString(){
        String str = "{ ";
        for (int i = 0; i < this.length; i++) {
            str += this.getNodeByIndex(i).getValue().toString();
            if (i < this.getPointsCount() - 1 && str.length() > 2) {
                str += ", ";
            }
        }
        str += " }";
        return str;
    }

    private boolean equalsSupCheck(LinkedListTabulatedFunction f){
        int i = 0;
        FunctionNode selfPtr = this.head.getNext();
        FunctionNode otherPtr = f.head.getNext();
        boolean flag = true;
        while (flag && this.length - i != 0){
            flag = otherPtr.getValue().equals(selfPtr.getValue());
            selfPtr = selfPtr.getNext();
            otherPtr = otherPtr.getNext();
            i++;
        }
        return flag;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;
        if (!(o instanceof TabulatedFunction)) {
            return false;
        }
        if (o instanceof LinkedListTabulatedFunction f) {
            if (this.length != f.getPointsCount())
                return false;
            return this.equalsSupCheck(f);
        } else {
            TabulatedFunction f = (TabulatedFunction) o;
            if (this.length != f.getPointsCount())
                return false;
            FunctionNode selfPtr = this.head.getNext();
            for (int i = 0; i < this.length; i++) {
                if (selfPtr.getValue().getX() != f.getPoint(i).getX() || selfPtr.getValue().getY() != f.getPoint(i).getY())
                    return false;
                selfPtr = selfPtr.getNext();
            }
            return true;
        }
    }



        @Override
        public int hashCode() {
            int result = 1; //чтобы отличать от нулевых объектов
            FunctionNode current = head;
            for (int i = 0; i < this.length; i++) {
                int pointHashCode = current.hashCode();
                result = 31 * result + pointHashCode;
                current = current.getNext();
            }
            return result;
        }

    @Override
    public Object clone(){
        LinkedListTabulatedFunction f = new LinkedListTabulatedFunction();
        for (int i = 0; i < this.length; i++)
            f.addNodeByIndex(i, this.getNodeByIndex(i).getValue());
        return f;
    }
    }
