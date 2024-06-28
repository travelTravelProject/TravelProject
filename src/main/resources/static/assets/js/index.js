document.addEventListener('DOMContentLoaded', () => {
  let currentSection = 0;
  const sections = document.querySelectorAll('.section');
  const dots = document.querySelectorAll('.dot');
  const totalSections = sections.length;

  const updateDots = () => {
      dots.forEach((dot, index) => {
          if (index === currentSection) {
              dot.classList.add('active');
          } else {
              dot.classList.remove('active');
          }
      });
  };

  const scrollToSection = (index) => {
      sections[index].scrollIntoView({
          behavior: 'smooth'
      });
      currentSection = index;
      updateDots();
  };

  document.addEventListener('wheel', (event) => {
      if (event.deltaY > 0) {
          if (currentSection < totalSections - 1) {
              currentSection++;
          }
      } else {
          if (currentSection > 0) {
              currentSection--;
          }
      }
      scrollToSection(currentSection);
  });

  document.addEventListener('keydown', (event) => {
      if (event.key === 'ArrowDown') {
          if (currentSection < totalSections - 1) {
              currentSection++;
          }
      } else if (event.key === 'ArrowUp') {
          if (currentSection > 0) {
              currentSection--;
          }
      }
      scrollToSection(currentSection);
  });

  dots.forEach((dot, index) => {
      dot.addEventListener('click', () => {
          scrollToSection(index);
      });
  });

  // 초기 상태에서 첫 번째 도트를 활성화
  updateDots();
});
