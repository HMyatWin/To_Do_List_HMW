import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.Scanner;

// タスククラス
class Task {
    private String description;  // タスク内容
    private Date date;           // タスクの日付
    private boolean isCompleted; // タスクが完了しているかどうか

    // コンストラクタ
    public Task(String description, Date date) {
        this.description = description;
        this.date = date;
        this.isCompleted = false;
    }

    // タスクの日付を取得
    public Date getDate() {
        return date;
    }

    // タスクの内容を取得
    public String getDescription() {
        return description;
    }

    // タスクが完了しているかどうかを取得
    public boolean isCompleted() {
        return isCompleted;
    }

    // タスクを完了させる
    public void markAsCompleted() {
        this.isCompleted = true;
    }

    // タスク情報を文字列に変換
    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return (isCompleted ? "[完了]" : "[未完了]") + " " + dateFormat.format(date) + " - " + description;
    }
}

// ToDoリストクラス
class TodoList {
    private ArrayList<Task> tasks = new ArrayList<>();  // タスクのリスト



    // 新しいタスクを追加
    public void addTask(String description, String dateStr) throws ParseException {
        if (description.isEmpty() || dateStr.isEmpty()) {
            System.out.println("エラー: タスク内容または日付が設定されていません。");
            return;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = dateFormat.parse(dateStr);
        Task newTask = new Task(description, date);
        tasks.add(newTask);
        sortTasks();
    }

    // タスクを完了にする
    public void completeTask(int taskIndex) {
        if (taskIndex >= 0 && taskIndex < tasks.size()) {
            Task task = tasks.get(taskIndex);
            if (!task.isCompleted()) {
                task.markAsCompleted();
                System.out.println("タスクが完了しました。");
                sortTasks();
            } else {
                System.out.println("このタスクはすでに完了済みです。");
            }
        } else {
            System.out.println("無効なタスク番号です。");
        }
    }

    // タスクを削除
    public void deleteTask(int taskIndex) {
        if (taskIndex >= 0 && taskIndex < tasks.size()) {
            Task task = tasks.get(taskIndex);
            if (task.isCompleted()) {
                tasks.remove(taskIndex);
                System.out.println("タスクが削除されました。");
            } else {
                System.out.println("未完了のタスクは削除できません。");
            }
        } else {
            System.out.println("無効なタスク番号です。");
        }
    }

    // タスクを一覧表示
    public void listTasks() {
        System.out.println("現在のTo-Doリスト:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println(i + ": " + tasks.get(i).toString());
        }
    }

    // 未完了のタスクは新しい順、完了済みのタスクも新しい順でソート
    private void sortTasks() {
        tasks.sort(Comparator.comparing(Task::isCompleted).thenComparing(Task::getDate).reversed());
    }
}

// メインクラス
public class Main {
    public static void main(String[] args) throws ParseException {
        Scanner scanner = new Scanner(System.in);
        TodoList todoList = new TodoList();

        while (true) {
            System.out.println("\n1. タスクを追加");
            System.out.println("2. タスクを完了");
            System.out.println("3. タスクを削除");
            System.out.println("4. タスクを表示");
            System.out.println("5. 終了");
            System.out.print("選択してください: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // 改行を消費

            switch (choice) {
                case 1:
                    // タスクを追加
                    System.out.print("タスク内容を入力してください: ");
                    String description = scanner.nextLine();
                    System.out.print("タスク日付を入力してください (yyyy-MM-dd): ");
                    String dateStr = scanner.nextLine();
                    todoList.addTask(description, dateStr);
                    break;

                case 2:
                    // タスクを完了
                    todoList.listTasks();
                    System.out.print("完了するタスクの番号を入力してください: ");
                    int taskNumToComplete = scanner.nextInt();
                    todoList.completeTask(taskNumToComplete);
                    break;

                case 3:
                    // タスクを削除
                    todoList.listTasks();
                    System.out.print("削除するタスクの番号を入力してください: ");
                    int taskNumToDelete = scanner.nextInt();
                    todoList.deleteTask(taskNumToDelete);
                    break;

                case 4:
                    // タスクを表示
                    todoList.listTasks();
                    break;

                case 5:
                    // 終了
                    System.out.println("終了します...");
                    scanner.close();
                    return;

                default:
                    System.out.println("無効な選択です。再度入力してください。");
            }
        }
    }
}
