<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.abc.entities.User" %>
<%@ page import="java.util.List" %>
<%@ page session="true"%>

<%
    com.abc.entities.User user = (com.abc.entities.User) session.getAttribute("user");
    if (user == null) response.sendRedirect("login");
%>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Tìm kiếm người dùng</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="d-flex flex-column min-vh-100 bg-light">
    <!-- Navbar -->
    <nav class="navbar navbar-expand-lg navbar-dark bg-primary fixed-top">
        <div class="container">
            <a class="navbar-brand" href="profile">🏠 Trang chủ</a>
        </div>
        <div class="navbar-nav ms-auto d-flex align-items-center">
            <a href="profile"> <span class="text-white me-3">👤 Hồ sơ của bạn</span>
                <img src="${pageContext.request.contextPath}/resources/images/avt.jpg"
                     alt="Avatar" class="rounded-circle" width="40">
            </a>
        </div>
    </nav>

    <div class="container mt-5 pt-5 flex-grow-1">
        <h3 class="mb-4">🔍 Tìm kiếm người dùng</h3>

        <!-- Search Form -->
        <form action="search" method="get" class="row g-3 mb-4">
		    <div class="col-md-10">
		        <input type="text" name="username" class="form-control" placeholder="Tên người dùng">
		    </div>
		    <div class="col-md-5">
		        <input type="number" name="minFollowers" class="form-control" placeholder="Tối thiểu số người theo dõi">
		    </div>
		    <div class="col-md-5">
		        <input type="number" name="minFollowing" class="form-control" placeholder="Tối thiểu số người đang theo dõi">
		    </div>
		    <div class="col-md-2">
		        <button type="submit" class="btn btn-danger w-100">Tìm kiếm</button>
		    </div>
		</form>


        <!-- Kết quả tìm kiếm -->
        <div class="row">
            <%
                List<User> searchResults = (List<User>) request.getAttribute("searchResults");
                if (searchResults != null && !searchResults.isEmpty()) {
                    for (User u : searchResults) {
            %>
            <div class="col-md-4 mb-3">
                <div class="card p-3">
                    <div class="d-flex align-items-center">
                        <img src="${pageContext.request.contextPath}/resources/images/avt.jpg"
                             alt="Avatar" class="rounded-circle me-3" width="50">
                        <div>
                            <h6 class="mb-1"><%= u.getUsername() %></h6>
                            <p class="text-muted mb-0">ID: <%= u.getId() %></p>
                        </div>
                    </div>
                </div>
            </div>
            <%
                    }
                } else if (searchResults != null) {
            %>
            <div class="col-12">
                <p class="text-muted">Không tìm thấy người dùng nào phù hợp.</p>
            </div>
            <%
                }
            %>
        </div>
    </div>

    <footer class="bg-primary text-white text-center py-3 mt-auto">
        <div class="container">
            <p>Công ty TNHH Mạng Xã Hội Việt © 2025</p>
        </div>
    </footer>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>