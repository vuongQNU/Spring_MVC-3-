<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Chỉnh Sửa Hồ Sơ</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-5">
        <h2>Chỉnh Sửa Hồ Sơ</h2>
        <c:if test="${not empty errors}">
            <div class="alert alert-danger">
                ${errors.error}
            </div>
        </c:if>
        <form action="/edit" method="post" enctype="multipart/form-data">
            <div class="mb-3">
                <label class="form-label">Tên Đăng Nhập</label>
                <input type="text" class="form-control" value="${user.username}" disabled>
            </div>
            <div class="mb-3">
                <label class="form-label">Email</label>
                <input type="email" name="email" class="form-control" value="${user.email}" required>
            </div>
            <div class="mb-3">
                <label class="form-label">Ngày Sinh</label>
                <input type="date" name="birthday" class="form-control" value="${user.birthday}" required>
            </div>
            <div class="mb-3">
                <label class="form-label">Tỉnh/Thành</label>
                <select name="provinceId" class="form-control" required>
                    <option value="">Chọn Tỉnh/Thành</option>
                    <c:forEach var="province" items="${provinces}">
                        <option value="${province.idProvince}" <c:if test="${province.idProvince == user.province.idProvince}">selected</c:if>>${province.nameProvince}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="mb-3">
                <label class="form-label">Avatar Hiện Tại</label><br>
                <c:if test="${not empty user.avatar}">
                    <img src="${user.avatar}" alt="Avatar" width="100">
                </c:if>
            </div>
            <div class="mb-3">
                <label class="form-label">Tải Lên Avatar Mới</label>
                <input type="file" name="avatarFile" class="form-control" accept="image/jpeg,image/png">
            </div>
            <button type="submit" class="btn btn-primary">Cập Nhật</button>
        </form>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>