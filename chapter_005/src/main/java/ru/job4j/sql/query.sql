--1. �������� ������ ��������� ���� ��������� � ����� "���"
SELECT * FROM product
WHERE type_id = (
  SELECT type.id
  FROM type
  WHERE name = '���');

--2. �������� ������ ��������� ���� ���������, � ���� � ����� ���� ����� "����������"
SELECT * FROM product
WHERE name like '%����������%';

--3. �������� ������, ������� ������� ��� ��������, ���� �������� ������� ������������� � ��������� ������.
SELECT * FROM product 
where (product.expired_date < '20180811');

--4. �������� ������, ������� ����� ����� ������� �������.
SELECT * FROM product
WHERE price = (
  SELECT MAX(price)
  FROM product
);
--5. �������� ������, ������� ������� ���������� ���� ��������� ������������� ����.
SELECT COUNT(type_id) FROM product
WHERE type_id = (
  SELECT type.id
  FROM type
  WHERE type.name = '������'
);
--6. �������� ������ ��������� ���� ��������� � ����� "���" � "������"
SELECT * FROM product
  WHERE type_id IN (
  SELECT type.id
  FROM type
  WHERE type.name = '���' OR type.name ='������');
 
  
  
--7. �������� ������, ������� ������� ��� ���������, ������� �������� ������ 10 ����. 
SELECT type.name
FROM product
INNER JOIN type ON product.type_id = type.id
GROUP BY type.name
HAVING count(product.id) < 10;
 

--8. ������� ��� �������� � �� ���.
SELECT product.name, type.name FROM product, type
WHERE product.type_id = type.id