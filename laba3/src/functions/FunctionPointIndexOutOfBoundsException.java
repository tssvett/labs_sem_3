package functions;

public class FunctionPointIndexOutOfBoundsException extends IndexOutOfBoundsException {
    public FunctionPointIndexOutOfBoundsException(){
        super();
    }
    public FunctionPointIndexOutOfBoundsException(String message){
        super("[!]" + message);
    }
    public FunctionPointIndexOutOfBoundsException(int index){
        super("[!] Ошибка! Выход за границы набора точек при обращении по индексу " + index);
    }

    public FunctionPointIndexOutOfBoundsException(long index){
        super("[!] Ошибка! Выход за границы набора точек при обращении по индексу " + index);
    }

}
