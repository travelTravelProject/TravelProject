<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>One Page Scroll Website</title>
    <style>
        body, html {
            margin: 0;
            padding: 0;
            height: 100%;
            overflow: hidden;
            scroll-behavior: smooth;
        }
        header {
            position: fixed;
            top: 0;
            width: 100%;
            background: #333;
            color: #fff;
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 10px 20px;
            z-index: 1000;
        }
        nav a {
            color: #fff;
            text-decoration: none;
            margin: 0 10px;
            font-size: 1em;
        }
        section {
            width: 100%;
            height: 100vh; /* Viewport height */
            display: flex;
            justify-content: center;
            align-items: center;
            font-size: 2em;
            transition: transform 0.5s ease-in-out;
        }
        #section1 { background-color: #ffadad; }
        #section2 { background-color: #ffd6a5; }
        #section3 { background-color: #fdffb6; }
        #section4 { background-color: #caffbf; }
        footer {
            background: #333;
            color: #fff;
            text-align: center;
            padding: 20px 0;
        }
        #toTopButton {
            position: fixed;
            bottom: 20px;
            right: 20px;
            padding: 10px 20px;
            font-size: 1em;
            background-color: #333;
            color: #fff;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            display: none;
        }
        #toTopButton.show {
            display: block;
        }
        #minimap {
            position: fixed;
            top: 50%;
            right: 20px;
            transform: translateY(-50%);
            display: flex;
            flex-direction: column;
            align-items: center;
        }
        .dot {
            width: 10px;
            height: 10px;
            background-color: #ddd;
            border-radius: 50%;
            margin: 5px 0;
            cursor: pointer;
        }
        .dot.active {
            background-color: #333;
        }
    </style>
</head>
<body>
    <header>
        <div>My Website</div>
        <nav>
            <a href="#section1">Section 1</a>
            <a href="#section2">Section 2</a>
            <a href="#section3">Section 3</a>
            <a href="#section4">Section 4</a>
        </nav>
    </header>

    <section id="section1">Section 1</section>
    <section id="section2">Section 2</section>
    <section id="section3">Section 3</section>
    <section id="section4">Section 4</section>

    <footer>
        <p>© 2024 My Website. All rights reserved.</p>
    </footer>

    <button id="toTopButton">Top</button>

    <div id="minimap">
        <div class="dot" data-section="0"></div>
        <div class="dot" data-section="1"></div>
        <div class="dot" data-section="2"></div>
        <div class="dot" data-section="3"></div>
    </div>

    <script>
        let currentSection = 0;
        const sections = document.querySelectorAll('section');
        const toTopButton = document.getElementById('toTopButton');
        const dots = document.querySelectorAll('.dot');
        let isScrolling = false;

        window.addEventListener('wheel', (event) => {
            if (isScrolling) return;
            isScrolling = true;

            if (event.deltaY > 0) {
                currentSection = Math.min(currentSection + 1, sections.length - 1);
            } else {
                currentSection = Math.max(currentSection - 1, 0);
            }
            scrollToSection();
        });

        document.querySelectorAll('nav a').forEach((navLink, index) => {
            navLink.addEventListener('click', (event) => {
                event.preventDefault();
                currentSection = index;
                scrollToSection();
            });
        });

        dots.forEach((dot, index) => {
            dot.addEventListener('click', () => {
                currentSection = index;
                scrollToSection();
            });
        });

        function scrollToSection() {
            sections.forEach((section, index) => {
                section.style.transform = `translateY(-${currentSection * 100}vh)`;
            });

            updateDots();

            if (currentSection === 0) {
                toTopButton.classList.remove('show');
            } else {
                toTopButton.classList.add('show');
            }

            setTimeout(() => {
                isScrolling = false;
            }, 500);
        }

        function updateDots() {
            dots.forEach((dot, index) => {
                if (index === currentSection) {
                    dot.classList.add('active');
                } else {
                    dot.classList.remove('active');
                }
            });
        }

        toTopButton.addEventListener('click', () => {
            currentSection = 0;
            scrollToSection();
        });

        updateDots();

        if (currentSection === 0) {
            toTopButton.classList.remove('show');
        }
    </script>
</body>
</html>
