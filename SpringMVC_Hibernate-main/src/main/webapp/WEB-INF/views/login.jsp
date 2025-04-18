<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Trang Đăng Nhập</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background: linear-gradient(135deg, #7f00ff, #e100ff);
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
        }
        .login-container {
            background: #fff;
            border-radius: 15px;
            box-shadow: 0 10px 30px rgba(0, 0, 0, 0.3);
            overflow: hidden;
        }
        .login-image {
            flex: 1;
            background: url('Image/How-To-Say-Hello-in-10-Languages.jpg') center/cover no-repeat;
            min-height: 400px;
        }
        .login-form {
            flex: 1;
            padding: 40px;
            display: flex;
            flex-direction: column;
            justify-content: center;
        }
        .input-group-text {
            background-color: #f8f9fa;
        }
        .btn-success {
            background-color: #28a745;
            border: none;
            padding: 10px;
            font-weight: bold;
        }
        .btn-success:hover {
            background-color: #218838;
        }
        .error-message {
            color: red;
            font-size: 0.9rem;
            margin-top: 10px;
        }
        a {
            color: #007bff;
        }
        a:hover {
            color: #0056b3;
            text-decoration: underline;
        }
        @media (max-width: 768px) {
            .login-image {
                display: none;
            }
            .login-form {
                padding: 20px;
            }
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="row login-container">
            <!-- Left side (image) -->
            <div class="login-image d-none d-md-block"></div>

            <!-- Right side (form) -->
            <div class="login-form">
                <h2 class="fw-bold text-center mb-4">Đăng Nhập Thành Viên</h2>
                <form action="login" method="post">
                    <div class="mb-3">
                        <div class="input-group">
                            <span class="input-group-text">👤</span>
                            <input type="text" name="username" class="form-control" placeholder="Tên Đăng Nhập" required>
                        </div>
                    </div>
                    <div class="mb-3">
                        <div class="input-group">
                            <span class="input-group-text">🔒</span>
                            <input type="password" name="password" class="form-control" placeholder="Mật Khẩu" required>
                        </div>
                    </div>
                    <button type="submit" class="btn btn-success w-100">ĐĂNG NHẬP</button>
                    <p class="mt-3 mb-1 text-center">
                        <a href="#" class="text-decoration-none">Quên Tên Đăng Nhập / Mật Khẩu?</a>
                    </p>
                    <p class="text-center">
                        <a href="register" class="text-decoration-none">Tạo Tài Khoản Mới →</a>
                    </p>
                </form>
                <!-- Hiển thị thông báo lỗi nếu có -->
                <c:if test="${not empty error}">
                    <p class="error-message text-center">${error}</p>
                </c:if>
            </div>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>