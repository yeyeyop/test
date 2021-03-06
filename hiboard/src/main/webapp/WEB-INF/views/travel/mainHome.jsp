<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.icia.common.util.StringUtil"%>
<%@ page import="com.icia.web.util.HttpUtil"%>

<!DOCTYPE html>
<html lang="ko">

<head>
  <meta charset="utf-8">
  <meta content="width=device-width, initial-scale=1.0" name="viewport">

  <title>MyPortfolio Bootstrap Template - Works</title>
  <meta content="" name="description">
  <meta content="" name="keywords">

  <!-- Favicons -->
  <link href="resources/images/favicon.png" rel="icon">
  <link href="resources/images/apple-touch-icon.png" rel="apple-touch-icon">

  <!-- Google Fonts -->
  <link href="https://fonts.googleapis.com/css?family=https://fonts.googleapis.com/css?family=Inconsolata:400,500,600,700|Raleway:400,400i,500,500i,600,600i,700,700i" rel="stylesheet">

  <!-- Vendor CSS Files -->
  <link href="resources/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
  <link href="resources/vendor/icofont/icofont.min.css" rel="stylesheet">
  <link href="resources/vendor/aos/aos.css" rel="stylesheet">
  <link href="resources/vendor/line-awesome/css/line-awesome.min.css" rel="stylesheet">
  <link href="resources/vendor/owl.carousel/assets/owl.carousel.min.css" rel="stylesheet">

  <!-- Template Main CSS File -->
  <link href="resources/css/style.css" rel="stylesheet">
   
<script>


function popup(){
       var url = "/calendar/calendar.jsp";
       var name = "popup test";
       var option = "width = 500, height = 300, top = 100, left = 200, location = no"
       window.open(url, name, option);
  }



</script>
</head>

<body>
<%@ include file="/WEB-INF/views/include/teamNavigation.jsp" %>
  <main id="main">

    <!-- ======= Works Section ======= -->
    <section class="section site-portfolio">
      <div class="container">
        <div class="row mb-5 align-items-center">
          <div class="col-md-12 col-lg-6 mb-4 mb-lg-0" data-aos="fade-up">
            <a class="nav-link" href="#">
              <img src="resources/images/travel.png" width="50" height="50" alt="" loading="lazy"></a>
          </div>
          <div class="col-md-12 text-center" data-aos="fade-up" data-aos-delay="100">
            <div id="filters" class="filters">
              <a href="#" data-filter="*" class="active">여러사람</a>
              <a href="#" data-filter=".web">계획</a>
              <a href="#" data-filter=".design">with<a>
              <a href="#" data-filter=".branding">리뷰</a>
            </div>
          </div>
        </div>
      <hr/>
        <div class="text-center">
          <h4>어디로 떠나볼까요?</h4>
          <br/><br/>
        </div>
        <div id="portfolio-grid" class="row no-gutter" data-aos="fade-up" data-aos-delay="200">
          <div class="item web col-sm-6 col-md-4 col-lg-4 mb-4">
            <a href="work-single.html" class="item-wrap fancybox">
              <div class="work-info">
                <h3>속초</h3>
                <span>#LINK</span>
              </div>
              <img class="img-fluid" src="resources/images/mg1.png">
            </a>
          </div>
          <div class="item photography col-sm-6 col-md-4 col-lg-4 mb-4">
            <a href="work-single.html" class="item-wrap fancybox">
              <div class="work-info">
                <h3>강릉</h3>
                <span>#LINK</span>
              </div>
              <img class="img-fluid" src="resources/images/mg2.png">
            </a>
          </div>
          <div class="item branding col-sm-6 col-md-4 col-lg-4 mb-4">
            <a href="work-single.html" class="item-wrap fancybox">
              <div class="work-info">
                <h3>제주</h3>
                <span>#LINK</span>
              </div>
              <img class="img-fluid" src="resources/images/mg3.png">
            </a>
          </div>
        </div>
      </div>
    </section><!-- End  Works Section -->

    <!-- ======= Testimonials Section ======= -->
    <section class="section pt-0">
      <div class="container">

        <div class="owl-carousel testimonial-carousel">

          <div class="testimonial-wrap">
            <div class="testimonial">
              <img src="resources/images/mg4.png" alt="Image" class="img-fluid">
              <blockquote>
                <p>빈칸</p>
              </blockquote>
              <p>&mdash; 빈칸</p>
            </div>
          </div>

          <div class="testimonial-wrap">
            <div class="testimonial">
              <img src="resources/images/mg5.png" alt="Image" class="img-fluid">
              <blockquote>
                <p>빈칸</p>
              </blockquote>
              <p>&mdash; 빈칸</p>
            </div>
          </div>

        </div>

      </div>
    </section><!-- End Testimonials Section -->

  </main><!-- End #main -->

  <!-- ======= Footer ======= -->
  <footer class="footer" role="contentinfo">
    <div class="container">
      <div class="row">
        <div class="col-sm-6">
          <p class="mb-1">&copy; Copyright MyPortfolio. All Rights Reserved</p>
          <div class="credits">
            Designed by <a href="https://bootstrapmade.com/">BootstrapMade</a>
          </div>
        </div>
        <div class="col-sm-6 social text-md-right">
          <a href="#"><span class="icofont-twitter"></span></a>
          <a href="#"><span class="icofont-facebook"></span></a>
          <a href="#"><span class="icofont-dribbble"></span></a>
          <a href="#"><span class="icofont-behance"></span></a>
        </div>
      </div>
    </div>
  </footer>

  <a href="#" class="back-to-top"><i class="icofont-simple-up"></i></a>

  <!-- Vendor JS Files -->
  <script src="resources/vendor/jquery/jquery.min.js"></script>
  <script src="resources/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
  <script src="resources/vendor/jquery.easing/jquery.easing.min.js"></script>
  <script src="resources/vendor/php-email-form/validate.js"></script>
  <script src="resources/vendor/aos/aos.js"></script>
  <script src="resources/vendor/isotope-layout/isotope.pkgd.min.js"></script>
  <script src="resources/vendor/owl.carousel/owl.carousel.min.js"></script>

  <!— Template Main JS File —>
  <script src="resources/js/main.js"></script>

</body>

</html>