INSERT INTO `projectbot`.`category` (`id`, `description`) VALUES ('1', 'Vestimentas');
INSERT INTO `projectbot`.`category` (`id`, `description`, `category_parent_id`) VALUES ('2', 'Roupas', '1');
INSERT INTO `projectbot`.`category` (`id`, `description`,`category_parent_id`) VALUES ('3', 'Calçados', '1');
INSERT INTO `projectbot`.`category` (`id`, `description`,`category_parent_id`) VALUES ('4', 'Bermudas', '1');
INSERT INTO `projectbot`.`product` (`id`, `description`, `info_tec`, `price`, `category_id`) VALUES ('1', 'Camiseta flex', 'maleavel', '23.90', '2');
INSERT INTO `projectbot`.`product` (`id`, `description`, `info_tec`, `price`, `category_id`) VALUES ('2', 'Camiseta ', 'vida marinha', '53.90', '2');