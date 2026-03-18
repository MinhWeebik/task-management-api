# Hệ thống Quản lý Công việc - REST API

Xây dựng REST API cho một hệ thống quản lý công việc đơn giản bằng Spring Boot.

## Yêu cầu kỹ thuật

- Java 17
- Spring Boot
- Spring JDBC
- Maven
- MySQL

## Hướng dẫn cài đặt và chạy dự án

1.  **Clone repository:**
    ```bash
    git clone https://github.com/MinhWeebik/task-management-api.git
    cd task-management-api
    ```

2.  **Cấu hình Cơ sở dữ liệu:**
    - Mở MySQL và tạo một database tên là `task_management`.
    - Chạy các câu lệnh SQL trong file `database_setup.sql` để khởi tạo các bảng `users` và `tasks`.

3.  **Cập nhật file cấu hình:**
    - Mở file `src/main/resources/application.properties`.
    - Cập nhật các thông tin `spring.datasource.url`, `spring.datasource.username`, và `spring.datasource.password` cho phù hợp với môi trường của bạn.
    ```properties
    spring.datasource.url=jdbc:mysql://localhost:3306/task_management
    spring.datasource.username=root
    spring.datasource.password=your_password
    ```

4.  **Build và chạy ứng dụng:**
    - Sử dụng Maven để build dự án:
    ```bash
    mvn clean install
    ```
    - Chạy ứng dụng:
    ```bash
    mvn spring-boot:run
    ```
    - Ứng dụng sẽ chạy tại địa chỉ `http://localhost:8080`.

## Danh sách các API

| Chức năng             | Method | Endpoint                    | Body Request                                                                                 |
| --------------------- | ------ | --------------------------- |----------------------------------------------------------------------------------------------|
| **User**              |        |                             |                                                                                              |
| Tạo User mới          | `POST` | `/users`                    | `{"name": "...", "email": "..."}`                                                            |
| Lấy danh sách User    | `GET`  | `/users`                    | *Query Params: `page`, `size`, `name`*                                                       |
| **Task**              |        |                             |                                                                                              |
| Tạo Task mới          | `POST` | `/tasks`                    | `{"title": "...", "assigneeId": ..., "dueDate": "YYYY-MM-DD"}`                               |
| Cập nhật trạng thái   | `PATCH`| `/tasks/{id}/status`        | `{"status": "IN_PROGRESS"}`                                                                  |
| Lấy danh sách Task    | `GET`  | `/tasks`                    | *Query Params: `assigneeId`, `status`, `dueDateStart`,`dueDateEnd`,`sortBy`, `page`, `size`* |
| Lấy chi tiết Task     | `GET`  | `/tasks/{id}`               |                                                                                              |
