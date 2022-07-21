public class Main {
    public static void main(String[] args) throws Exception {
        var t = "function (x, y) { 3 + 5 * 4 }";
        var t2 = "function add(x, y) { let z = (3 + 5) * 10 ^ 2 + x + y; return z; } let y = 800 + 12;";
        var lexer = new Lexer(t2);
        var parser = new Parser(lexer);
        var interpeter = new Interpreter(parser.parse());
        System.out.println(interpeter.interpret());
    }
}
