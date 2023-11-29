import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Review05 {

    public static void main(String[] args) {
     // 3. データベース接続と結果取得のための変数宣言
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
         // 1. ドライバのクラスをJava上で読み込む
            Class.forName("com.mysql.cj.jdbc.Driver");

         // 2. DBと接続する
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost/kadaidb?useSSL=false&allowPublicKeyRetrieval=true",
                    "root",
                    "XYKFC164ma"
                );

         // 4. DBとやりとりする窓口（PreparedStatementオブジェクト）の作成
            String selectSql = "SELECT * FROM person where id = ?";
            pstmt = con.prepareStatement(selectSql);

         // 更新するキーワードidを入力
            System.out.print("検索キーワードを入力してください > ");
            int num1 = keyInNum();

         // 入力されたキーワードidをPreparedStatementオブジェクトにセット
            pstmt.setInt(1,num1);

         // 5, 6. Select文の実行と結果を格納／代入
            rs = pstmt.executeQuery();

         // 7. 結果を表示する
            while( rs.next() ){
                String name = rs.getString("Name");
                int age = rs.getInt("Age");

                System.out.println(name);
                System.out.println(age);
            }

        } catch (ClassNotFoundException e) {
            System.err.println("JDBCドライバのロードに失敗しました。");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("データベースに異常が発生しました。");
            e.printStackTrace();
        } finally {
        // 8. 接続を閉じる
            if( rs != null ){
                try {
                    rs.close();
                } catch (SQLException e) {
                    System.err.println("ResultSetを閉じるときにエラーが発生しました。");
                    e.printStackTrace();
                }
            }
            if(pstmt != null ){
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    System.err.println("PreparedStatementを閉じるときにエラーが発生しました。");
                    e.printStackTrace();
                }
            }
            if( con != null ){
                try {
                    con.close();
                } catch (SQLException e) {
                    System.err.println("データベース切断時にエラーが発生しました。");
                    e.printStackTrace();
                }
            }
        }
    }
    private static String keyIn() {
        String line = null;
        try {
            BufferedReader key = new BufferedReader(new InputStreamReader(System.in));
            line = key.readLine();
        } catch (IOException e) {

        }
        return line;
    }

    private static int keyInNum() {
        int result = 0;
        try {
            result = Integer.parseInt(keyIn());
        } catch (NumberFormatException e) {
        }
        return result;
    }



}
